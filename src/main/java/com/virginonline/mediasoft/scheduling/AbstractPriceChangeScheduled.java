package com.virginonline.mediasoft.scheduling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractPriceChangeScheduled {

  @Value("${app.priceChangePercentage}")
  protected Integer priceChangePercentage;

  protected abstract void execute();

  protected BigDecimal getNewPrice(BigDecimal oldPrice) {
    return oldPrice
        .multiply(BigDecimal.valueOf(1 + (priceChangePercentage / 100.0)))
        .setScale(2, RoundingMode.HALF_UP);
  }
}
