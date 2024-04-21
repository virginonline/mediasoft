package com.virginonline.mediasoft.criteria.field;

import com.virginonline.mediasoft.criteria.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NameField extends Field {

  String value;

  public NameField(Operation operation, String value) {
    super("name", operation);
    this.value = value;
  }
}
