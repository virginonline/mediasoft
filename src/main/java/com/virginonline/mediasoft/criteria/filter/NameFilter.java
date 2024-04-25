package com.virginonline.mediasoft.criteria.filter;

import com.virginonline.mediasoft.criteria.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameFilter extends Filter<String> {

  public NameFilter(@NotNull Operation operation, String value) {
    super("name", operation, value);
  }
}
