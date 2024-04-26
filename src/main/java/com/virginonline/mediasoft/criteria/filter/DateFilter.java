package com.virginonline.mediasoft.criteria.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virginonline.mediasoft.criteria.Operation;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DateFilter extends Filter<Instant> {

  public DateFilter(
      @NotNull(message = "Operation cannot be null") Operation operation,
      @NotNull(message = "Value cannot be null") Instant value) {
    super("createdAt", operation, value);
  }

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  @NotNull
  public Instant getValue() {
    return super.getValue();
  }
}
