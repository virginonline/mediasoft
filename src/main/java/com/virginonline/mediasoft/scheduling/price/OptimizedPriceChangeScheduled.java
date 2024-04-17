package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.annotation.Timed;
import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import jakarta.persistence.LockModeType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class OptimizedPriceChangeScheduled extends AbstractPriceChangeScheduled {

  private final JdbcTemplate jdbcTemplate;

  @Timed
  @Scheduled(fixedRateString = "${app.scheduling.period}")
  @Transactional(rollbackFor = Exception.class)
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  protected void execute() {
    try (var writer =
        Files.newBufferedWriter(
            Paths.get("log.txt"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
      var products =
          jdbcTemplate.query(
              "select article, price from products for update",
              new BeanPropertyRowMapper<>(Product.class));
      jdbcTemplate.batchUpdate(
          "update products set price = ?, updated_at = now() where article = ?",
          new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
              var newPrice = getNewPrice(products.get(i).getPrice());
              ps.setBigDecimal(1, newPrice);
              ps.setLong(2, products.get(i).getArticle());
              logProduct(i, writer, products.get(i), newPrice);
            }

            @Override
            public int getBatchSize() {
              return products.size();
            }
          });
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void logProduct(int i, BufferedWriter writer, Product product, BigDecimal newPrice) {
    if ((i + 1) % 100000 == 0) {
      log.info("Processed {} products", i + 1);
    }
    try {
      writer.write(
          "%d Product article: %d, price: %.2f new price: %.2f\n"
              .formatted(i + 1, product.getArticle(), product.getPrice(), newPrice));
      writer.flush();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
