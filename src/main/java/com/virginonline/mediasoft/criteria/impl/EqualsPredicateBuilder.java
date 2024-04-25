package com.virginonline.mediasoft.criteria.impl;

import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.PredicateBuilder;
import com.virginonline.mediasoft.criteria.filter.DateFilter;
import com.virginonline.mediasoft.criteria.filter.Filter;
import com.virginonline.mediasoft.criteria.filter.NameFilter;
import com.virginonline.mediasoft.criteria.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class EqualsPredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return Operation.EQUALS;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Filter filter) {
    return switch (filter.getClass().getSimpleName()) {
      case "NumericFilter" -> cb.equal(path, ((NumericFilter) filter).getValue());
      case "DateFilter" -> cb.equal(path, ((DateFilter) filter).getValue());
      case "NameFilter" -> cb.equal(path, ((NameFilter) filter).getValue());
      default -> cb.isNull(path);
    };
  }
}
