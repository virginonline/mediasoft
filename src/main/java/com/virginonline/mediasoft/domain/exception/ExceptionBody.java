package com.virginonline.mediasoft.domain.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {

  private String message;
  private Map<String, String> error;

  public ExceptionBody(String message) {
    this.message = message;
  }
}
