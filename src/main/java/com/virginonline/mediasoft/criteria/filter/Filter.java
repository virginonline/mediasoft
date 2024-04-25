package com.virginonline.mediasoft.criteria.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.virginonline.mediasoft.criteria.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "field", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(name = "price", value = NumericFilter.class),
  @JsonSubTypes.Type(name = "createdAt", value = DateFilter.class),
  @JsonSubTypes.Type(name = "name", value = NameFilter.class)
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Filter<T> {

  @NotBlank protected String field;

  @NotNull(message = "Operation cannot be null")
  protected Operation operation;

  @NotNull(message = "Value cannot be null")
  protected T value;
}
