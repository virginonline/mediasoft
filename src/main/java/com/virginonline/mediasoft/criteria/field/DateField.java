package com.virginonline.mediasoft.criteria.field;

import com.virginonline.mediasoft.criteria.Operation;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateField extends Field {

  private Instant value;

  public DateField(Operation operation, Instant value) {
    super("date", operation);
    this.value = value.truncatedTo(ChronoUnit.SECONDS);
  }

  public DateField(Instant value) {
    this.value = value.truncatedTo(ChronoUnit.SECONDS);
  }
}
