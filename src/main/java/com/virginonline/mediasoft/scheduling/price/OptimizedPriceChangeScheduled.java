package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.annotation.Timed;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Optimized price scheduled avg time for execute 46sec */
@Profile("prod")
@ConditionalOnExpression(
    "${app.scheduling.enabled:true} == true && '${app.scheduling.mode}'.equals('optimized')")
@Slf4j
@Component
@RequiredArgsConstructor
public class OptimizedPriceChangeScheduled extends AbstractPriceChangeScheduled {

  private final JdbcTemplate jdbcTemplate;
  private final Integer BATCH_SIZE = 100_000;

  @Timed
  @Scheduled(fixedRateString = "${app.scheduling.period}")
  protected void execute() {
    try (Connection connection =
            Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        var writer =
            Files.newBufferedWriter(
                Paths.get("log.txt"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
      connection.setAutoCommit(false);
      processProducts(connection, writer);
      connection.commit();
    } catch (SQLException | IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void processProducts(Connection connection, BufferedWriter writer) throws SQLException {
    List<UUID> updatedProductIds = new ArrayList<>();
    log.info("Lock table products for update");
    lockTable(connection);
    try (Statement statement = connection.createStatement();
        ResultSet selectResultSet =
            statement.executeQuery("SELECT id, price FROM products FOR UPDATE")) {
      PreparedStatement updateStatement =
          connection.prepareStatement("UPDATE products SET price = ? WHERE id = ?");
      int rowsCount = 0;
      while (selectResultSet.next()) {
        BigDecimal newPrice = getNewPrice(selectResultSet.getBigDecimal("price"));
        updateStatement.setBigDecimal(1, newPrice);
        updateStatement.setObject(2, UUID.fromString(selectResultSet.getObject("id").toString()));
        updateStatement.addBatch();
        updatedProductIds.add(UUID.fromString(selectResultSet.getString("id")));
        if (++rowsCount % BATCH_SIZE == 0) {
          updateStatement.executeBatch();
          logProduct(rowsCount, writer, updatedProductIds);
          updatedProductIds.clear();
          writer.flush();
        }
      }
    } catch (SQLException | IOException e) {
      connection.rollback();
      throw new RuntimeException(e);
    }
  }

  private void lockTable(Connection connection) {
    try (PreparedStatement lockQuery =
        connection.prepareStatement("lock table products in exclusive mode")) {
      lockQuery.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void logProduct(int rowsCount, BufferedWriter writer, List<UUID> uuids) {
    log.info("Updated {} rows", rowsCount);
    uuids.forEach(
        id -> {
          try {
            writer.write("Product id: %s updated \n".formatted(id.toString()));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
