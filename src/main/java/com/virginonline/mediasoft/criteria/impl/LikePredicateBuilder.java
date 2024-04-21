package com.virginonline.mediasoft.criteria.impl;

import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.PredicateBuilder;
import com.virginonline.mediasoft.criteria.field.Field;
import com.virginonline.mediasoft.criteria.field.NameField;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class LikePredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return Operation.LIKE;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Field field) {
    if (field instanceof NameField) {
      return cb.like(path, "%" + ((NameField) field).getValue() + "%");
    }
    return cb.isNull(path);
  }
}
