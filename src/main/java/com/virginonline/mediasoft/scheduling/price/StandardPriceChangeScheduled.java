package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.annotation.Timed;
import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.repository.ProductRepository;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used stream api to update all products avg time for execute 800sec
 *
 * @deprecated use {@link OptimizedPriceChangeScheduled}
 */
@Profile("prod")
@ConditionalOnExpression(
    "${app.scheduling.enabled} == true and '${app.scheduling.mode}'.equals('standard')")
@Slf4j
@Component
@RequiredArgsConstructor
public class StandardPriceChangeScheduled extends AbstractPriceChangeScheduled {

  private final ProductRepository productRepository;

  @Timed
  @Scheduled(fixedRateString = "${app.scheduling.period}")
  @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
  @Lock(value = LockModeType.OPTIMISTIC)
  @Override
  protected void execute() {
    List<Product> products =
        productRepository.findAll().stream()
            .map(
                product -> {
                  product.setPrice(getNewPrice(product.getPrice()));
                  return product;
                })
            .collect(Collectors.toList());
    productRepository.saveAll(products);
  }
}
