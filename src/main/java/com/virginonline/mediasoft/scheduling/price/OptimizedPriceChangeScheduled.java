package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class OptimizedPriceChangeScheduled extends AbstractPriceChangeScheduled {

  private final JdbcTemplate jdbcTemplate;

  @Scheduled(fixedRateString = "${app.scheduling.period}")
  protected void execute() {}
}
