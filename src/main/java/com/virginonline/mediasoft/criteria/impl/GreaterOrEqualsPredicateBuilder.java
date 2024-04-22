package com.virginonline.mediasoft.criteria.impl;

import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.PredicateBuilder;
import com.virginonline.mediasoft.criteria.field.DateField;
import com.virginonline.mediasoft.criteria.field.Field;
import com.virginonline.mediasoft.criteria.field.PriceField;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class GreaterOrEqualsPredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return null;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Field field) {
    return switch (field.getClass().getSimpleName()) {
      case "PriceField" -> cb.greaterThanOrEqualTo(path, ((PriceField) field).getValue());
      case "DateField" -> cb.greaterThanOrEqualTo(path, ((DateField) field).getValue());
      default -> cb.isNull(path);
    };
  }
}
