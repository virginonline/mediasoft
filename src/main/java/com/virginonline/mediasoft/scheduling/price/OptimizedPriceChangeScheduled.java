package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.annotation.Timed;
import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  protected void execute() {
    var products =
        jdbcTemplate.query(
            "select article, price from products", new BeanPropertyRowMapper<>(Product.class));
    jdbcTemplate.batchUpdate(
        "update products set price = ?, updated_at = now() where article = ?",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setBigDecimal(1, getNewPrice(products.get(i).getPrice()));
            ps.setLong(2, products.get(i).getArticle());
          }

          @Override
          public int getBatchSize() {
            return products.size();
          }
        });
  }
}
