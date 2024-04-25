package com.virginonline.mediasoft.criteria;

import com.virginonline.mediasoft.criteria.filter.Filter;
import com.virginonline.mediasoft.criteria.impl.EqualsPredicateBuilder;
import com.virginonline.mediasoft.criteria.impl.GreaterOrEqualsPredicateBuilder;
import com.virginonline.mediasoft.criteria.impl.GreaterPredicateBuilder;
import com.virginonline.mediasoft.criteria.impl.LessOrEqualsPredicateBuilder;
import com.virginonline.mediasoft.criteria.impl.LessPredicateBuilder;
import com.virginonline.mediasoft.criteria.impl.LikePredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.jpa.domain.Specification;

public class JpaSpecificationsBuilder<T> {

  private final Map<Operation, PredicateBuilder> predicateBuilders =
      Stream.of(
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.EQUALS, new EqualsPredicateBuilder()),
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.GREATER_THAN, new GreaterPredicateBuilder()),
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.GRATER_THAN_OR_EQ, new GreaterOrEqualsPredicateBuilder()),
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.LESS_THAN, new LessPredicateBuilder()),
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.LESS_THAN_OR_EQ, new LessOrEqualsPredicateBuilder()),
              new AbstractMap.SimpleEntry<Operation, PredicateBuilder>(
                  Operation.LIKE, new LikePredicateBuilder()))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

  public Specification<T> buildSpecification(List<Filter> criterion) {
    return (root, query, cb) -> buildPredicate(root, cb, criterion);
  }

  private Predicate buildPredicate(Root<T> root, CriteriaBuilder cb, List<Filter> criterion) {
    List<Predicate> predicates = new ArrayList<>();
    criterion.forEach(
        field -> {
          if (field.getOperation() != null) {
            PredicateBuilder predicateBuilder = predicateBuilders.get(field.getOperation());
            if (predicateBuilder != null) {
              predicates.add(predicateBuilder.getPredicate(cb, root.get(field.getField()), field));
            }
          }
        });
    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
