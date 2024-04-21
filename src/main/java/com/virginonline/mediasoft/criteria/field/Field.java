package com.virginonline.mediasoft.criteria.field;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.virginonline.mediasoft.criteria.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "field", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(name = "price", value = PriceField.class),
  @JsonSubTypes.Type(name = "date", value = DateField.class),
  @JsonSubTypes.Type(name = "name", value = NameField.class)
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Field {
  protected String field;
  protected Operation operation;
}
