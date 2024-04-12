package com.virginonline.mediasoft.web.openapi;

import com.virginonline.mediasoft.web.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "product api", description = "Operations about products")
public interface ProductControllerOpenApi {

  @GetMapping
  @Operation(description = "Get products", operationId = "getAllProducts")
  ResponseEntity<List<ProductDto.Response.Default>> getAll(
      @Parameter(in = ParameterIn.QUERY, name = "category") @RequestParam(required = false, name = "category")
          Optional<String> category,
      @Parameter(in = ParameterIn.QUERY, example = "20") @RequestParam(defaultValue = "20")
          Integer limit,
      @Parameter(in = ParameterIn.QUERY, example = "0") @RequestParam(defaultValue = "0")
          Integer offset);

  @PostMapping
  @Operation(description = "Create new product", operationId = "createProduct")
  ResponseEntity<ProductDto.Response.Default> create(
      @Parameter(
              required = true,
              name = "Product",
              schema = @Schema(implementation = ProductDto.Request.Create.class))
          @RequestBody
          @Valid
          final ProductDto.Request.Create payload);

  @PatchMapping("/{id}")
  @Operation(description = "Update product", operationId = "updateProduct")
  ResponseEntity<ProductDto.Response.Default> update(
      @PathVariable(name = "id") UUID id,
      @RequestBody @Valid final ProductDto.Request.Patch payload);

  @GetMapping("/{id}")
  @Operation(description = "Get product by id", operationId = "getProductById")
  ResponseEntity<ProductDto.Response.Default> getById(
      @Parameter(in = ParameterIn.PATH, name = "id") @PathVariable(name = "id") UUID id);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete product by id", operationId = "deleteProductById")
  void delete(@Parameter(in = ParameterIn.PATH, name = "id") @PathVariable(name = "id") UUID id);
}
