package com.virginonline.mediasoft.service.impl;

import com.virginonline.mediasoft.annotation.Timed;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

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

  @Timed
  @Override
  public Product findById(UUID id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFound("Product with %s not found".formatted(id)));
  }

  @Timed
  @Override
  @Transactional
  public List<Product> findAll(Pageable pageable) {
    return productRepository.findAllPaginated(pageable);
  }

  @Override
  public List<Product> findAll(String category, Pageable pageable) {
    return productRepository.findAllByCategory(category, pageable);
  }
}
