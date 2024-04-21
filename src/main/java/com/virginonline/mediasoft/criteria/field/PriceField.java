package com.virginonline.mediasoft.criteria.field;

import com.virginonline.mediasoft.criteria.Operation;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceField extends Field {
  @NotNull private BigDecimal value;

  public PriceField(Operation operation, BigDecimal value) {
    super("price", operation);
    this.value = value;
  }
}
