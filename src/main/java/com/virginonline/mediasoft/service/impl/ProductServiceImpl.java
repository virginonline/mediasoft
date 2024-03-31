package com.virginonline.mediasoft.service.impl;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product create(ProductDto payload) {
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
  public Product update(UUID id, Product payload) {
    var existing = this.findById(id);
    existing.setArticle(payload.getArticle());
    existing.setName(payload.getName());
    existing.setCategory(payload.getCategory());
    existing.setDescription(payload.getDescription());
    existing.setQuantity(payload.getQuantity());
    existing.setPrice(payload.getPrice());
    return productRepository.save(existing);
  }

  @Override
  public Product findById(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFound("Product with %s not found".formatted(id)));
  }

  @Override
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> findAll(String category) {
    return productRepository.findAllByCategory(category);
  }
}
