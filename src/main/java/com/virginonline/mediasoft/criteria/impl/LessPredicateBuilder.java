package com.virginonline.mediasoft.criteria.impl;

import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.PredicateBuilder;
import com.virginonline.mediasoft.criteria.filter.DateFilter;
import com.virginonline.mediasoft.criteria.filter.Filter;
import com.virginonline.mediasoft.criteria.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class LessPredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return Operation.LESS_THAN;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Filter filter) {
    return switch (filter.getClass().getSimpleName()) {
      case "NumericFilter" -> cb.lessThan(path, ((NumericFilter) filter).getValue());
      case "DateFilter" -> cb.lessThan(path, ((DateFilter) filter).getValue());
      default -> cb.isNull(path);
    };
  }
}
