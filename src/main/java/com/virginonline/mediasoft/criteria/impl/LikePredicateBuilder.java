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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

public class LikePredicateBuilder implements PredicateBuilder {

  @Override
  public Operation getPredicateOperator() {
    return Operation.LIKE;
  }

  @Override
  public Predicate getPredicate(CriteriaBuilder cb, Path path, Filter filter) {
    return switch (filter.getClass().getSimpleName()) {
      case "NameFilter" -> cb.like(path, "%" + ((NameFilter) filter).getValue() + "%");
      case "DateFilter" ->
          cb.between(
              path,
              ((DateFilter) filter).getValue().minus(3, ChronoUnit.DAYS),
              ((DateFilter) filter).getValue().plus(3, ChronoUnit.DAYS));
      case "NumericFilter" ->
          cb.between(
              path,
              ((NumericFilter) filter)
                  .getValue()
                  .multiply(BigDecimal.valueOf(-10))
                  .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                  .setScale(2, RoundingMode.HALF_UP),
              ((NumericFilter) filter)
                  .getValue()
                  .multiply(BigDecimal.valueOf(10))
                  .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                  .setScale(2, RoundingMode.HALF_UP));
      default -> cb.isNull(path);
    };
  }
}
