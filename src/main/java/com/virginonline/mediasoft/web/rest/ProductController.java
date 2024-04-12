package com.virginonline.mediasoft.web.rest;

import com.virginonline.mediasoft.service.ProductService;
import com.virginonline.mediasoft.web.dto.ProductDto.Request.Create;
import com.virginonline.mediasoft.web.dto.ProductDto.Request.Patch;
import com.virginonline.mediasoft.web.dto.ProductDto.Response.Default;
import com.virginonline.mediasoft.web.mapper.ProductMapper;
import com.virginonline.mediasoft.web.openapi.ProductControllerOpenApi;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerOpenApi {

  private final ProductService productService;

  @Override
  public ResponseEntity<List<Default>> getAll(
      Optional<String> category, Integer limit, Integer offset) {
    log.info("getAll(category={}, limit={}, offset={})", category, limit, offset);
    var response =
        category
            .map(c -> productService.findAll(c, PageRequest.of(offset, limit)))
            .orElseGet(() -> productService.findAll(PageRequest.of(offset, limit)));
    return ResponseEntity.ok(ProductMapper.toDto(response));
  }

  @Override
  public ResponseEntity<Default> create(Create payload) {
    return ResponseEntity.ok(ProductMapper.toDto(productService.create(payload)));
  }

  @Override
  public ResponseEntity<Default> update(UUID id, Patch payload) {
    return ResponseEntity.ok(ProductMapper.toDto(productService.update(id, payload)));
  }

  @Override
  public ResponseEntity<Default> getById(UUID id) {
    return ResponseEntity.ok(ProductMapper.toDto(productService.findById(id)));
  }

  @Override
  public void delete(UUID id) {
    productService.delete(id);
  }
}
