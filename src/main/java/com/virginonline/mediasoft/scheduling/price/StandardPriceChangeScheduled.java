package com.virginonline.mediasoft.scheduling.price;

import com.virginonline.mediasoft.annotation.Timed;
import com.virginonline.mediasoft.repository.ProductRepository;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class StandardPriceChangeScheduled extends AbstractPriceChangeScheduled {

  private final ProductRepository productRepository;

  @Timed
  @Scheduled(fixedRateString = "${app.scheduling.period}")
  @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
  @Lock(value = LockModeType.OPTIMISTIC)
  @Override
  protected void execute() {
    var products =
        productRepository.findAll().stream()
            .peek(product -> getNewPrice(product.getPrice()))
            .toList();
    productRepository.saveAll(products);
  }
}
