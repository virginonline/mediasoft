package com.virginonline.mediasoft.web.rest;

import com.virginonline.mediasoft.service.ProductService;
import com.virginonline.mediasoft.web.dto.ProductDto;
import com.virginonline.mediasoft.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@Tag(name = "product api")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @Operation(description = "Get products", operationId = "getAllProducts")
  public List<ProductDto> getAll(
      @RequestParam(name = "category", required = false) Optional<String> category) {
    var products = category.map(productService::findAll).orElseGet(productService::findAll);
    return ProductMapper.toDto(products);
  }

  @PostMapping
  @Operation(description = "Create new product", operationId = "createProduct")
  public ProductDto create(@RequestBody @Valid ProductDto payload) {
    return ProductMapper.toDto(productService.create(payload));
  }

  @PatchMapping("/{id}")
  @Operation(description = "Update product", operationId = "updateProduct")
  public ProductDto update(@PathVariable(name = "id") UUID id,
      @RequestBody @Valid ProductDto payload) {
    return ProductMapper.toDto(productService.update(id, ProductMapper.toEntity(payload)));
  }

  @GetMapping("/{id}")
  @Operation(description = "Get product by id", operationId = "getProductById")
  public ProductDto getById(@PathVariable(name = "id") UUID id) {
    return ProductMapper.toDto(productService.findById(id));
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Delete product by id", operationId = "deleteProductById")
  public void deleteById(@PathVariable UUID id) {
    productService.delete(id);
  }
}
