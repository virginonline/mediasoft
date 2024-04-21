package com.virginonline.mediasoft.service.impl;

import com.virginonline.mediasoft.criteria.JpaSpecificationsBuilder;
import com.virginonline.mediasoft.criteria.field.Field;
import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.domain.exception.ArticleAlreadyExist;
import com.virginonline.mediasoft.domain.exception.ProductNotFound;
import com.virginonline.mediasoft.repository.ProductRepository;
import com.virginonline.mediasoft.service.ProductService;
import com.virginonline.mediasoft.web.dto.ProductDto;
import com.virginonline.mediasoft.web.mapper.ProductMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final JpaSpecificationsBuilder<Product> jpaSpecificationsBuilder =
      new JpaSpecificationsBuilder<>();

  @Override
  public Product create(ProductDto.Request.Create payload) {

    if (productRepository.existsByArticle(payload.article())) {
      throw new ArticleAlreadyExist(
          "Product with %d article already exist".formatted(payload.article()));
    }
    return productRepository.save(ProductMapper.toEntity(payload));
  }

  @Override
  public void delete(UUID id) {
    productRepository.deleteById(id);
  }

  @Override
  public Product update(UUID id, ProductDto.Request.Patch payload) {
    var existing = this.findById(id);
    existing.setArticle(payload.article());
    existing.setName(payload.name());
    existing.setCategory(payload.category());
    existing.setDescription(payload.description());
    existing.setQuantity(payload.quantity());
    existing.setPrice(payload.price());
    return productRepository.save(existing);
  }

  @Override
  public Product findById(UUID id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFound("Product with %s not found".formatted(id)));
  }

  @Override
  public List<Product> findAll(Pageable pageable) {
    return productRepository.findAll(pageable).getContent();
  }

  @Override
  public List<Product> findAllByCriteria(PageRequest of, List<Field> criteria) {
    var criteries = jpaSpecificationsBuilder.buildSpecification(criteria);
    var products = productRepository.findAll(criteries, of);
    return products.getContent();
  }
}
