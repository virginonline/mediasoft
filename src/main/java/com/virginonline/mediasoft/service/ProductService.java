package com.virginonline.mediasoft.service;

import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.web.dto.ProductDto;
import java.util.List;
import java.util.UUID;

public interface ProductService {

  Product create(ProductDto payload);

  void delete(UUID id);

  Product update(UUID id, Product payload);

  Product findById(UUID id);

  List<Product> findAll();

  List<Product> findAll(String category);
}
