package com.virginonline.mediasoft.service;

import com.virginonline.mediasoft.criteria.filter.Filter;
import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.web.dto.ProductDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Product create(ProductDto.Request.Create payload);

  void delete(UUID id);

  Product update(UUID id, ProductDto.Request.Patch payload);

  Product findById(UUID id);

  List<Product> findAll(Pageable pageable);

  List<Product> findAllByCriteria(PageRequest of, List<Filter> criteria);
}
