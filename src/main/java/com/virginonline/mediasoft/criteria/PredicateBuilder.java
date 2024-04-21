package com.virginonline.mediasoft.criteria;

import com.virginonline.mediasoft.criteria.field.Field;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public interface PredicateBuilder {
  Operation getPredicateOperator();

  Predicate getPredicate(CriteriaBuilder cb, Path path, Field field);
}
