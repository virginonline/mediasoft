package com.virginonline.mediasoft.criteria.impl;

import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.PredicateBuilder;
import com.virginonline.mediasoft.criteria.field.DateField;
import com.virginonline.mediasoft.criteria.field.Field;
import com.virginonline.mediasoft.criteria.field.NameField;
import com.virginonline.mediasoft.criteria.field.PriceField;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class EqualsPredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return Operation.EQUALS;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Field field) {
    return switch (field.getClass().getSimpleName()) {
      case "PriceField" -> cb.equal(path, ((PriceField) field).getValue());
      case "DateField" -> cb.equal(path, ((DateField) field).getValue());
      case "NameField" -> cb.equal(path, ((NameField) field).getValue());
      default -> cb.isNull(path);
    };
  }
}
