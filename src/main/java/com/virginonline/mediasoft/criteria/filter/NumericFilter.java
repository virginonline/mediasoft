package com.virginonline.mediasoft.criteria.filter;

import com.virginonline.mediasoft.criteria.Operation;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumericFilter extends Filter<BigDecimal> {

  public NumericFilter(@NotNull Operation operation, BigDecimal value) {
    super("price", operation, value);
  }
}
