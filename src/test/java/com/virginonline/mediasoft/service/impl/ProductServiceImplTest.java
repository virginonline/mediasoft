package com.virginonline.mediasoft.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.domain.exception.ProductNotFound;
import com.virginonline.mediasoft.repository.ProductRepository;
import com.virginonline.mediasoft.web.dto.ProductDto;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository mockProductRepository;

  private ProductServiceImpl productServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    productServiceImplUnderTest = new ProductServiceImpl(mockProductRepository);
  }


  @Test
  void givenNonExistingProduct_whenCreate_thenSaveProduct() {
    // Arrange
    final ProductDto payload = new ProductDto(
        UUID.fromString("a8e7412c-4972-424a-af2d-933af8a322f9"), "name", 0L, "category",
        "description",
        new BigDecimal("0.00"));
    when(mockProductRepository.existsByArticle(0L)).thenReturn(false);

    final Product product = Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build();
    when(mockProductRepository.save(any(Product.class))).thenReturn(product);

    // Act
    final Product result = productServiceImplUnderTest.create(payload);

    // Assert
    assertThat(result).isEqualTo(product);
  }

  @Test
  void givenProductId_whenDelete_thenDeleteProduct() {
    // Arrange
    // Act
    productServiceImplUnderTest.delete(UUID.fromString("078d8233-d8b2-4a19-a782-8c07eab94481"));

    // Assert
    verify(mockProductRepository).deleteById(
        UUID.fromString("078d8233-d8b2-4a19-a782-8c07eab94481"));
  }

  @Test
  void givenProductIdAndPayload_whenUpdate_thenUpdateProduct() {
    // Arrange
    final Product payload = Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build();

    final Optional<Product> product = Optional.of(Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("old_name")
        .article(1L)
        .description("old_description")
        .category("old_category")
        .price(new BigDecimal("1.00"))
        .build());
    when(mockProductRepository.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"))).thenReturn(product);

    final Product updatedProduct = Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build();
    when(mockProductRepository.save(any(Product.class))).thenReturn(updatedProduct);

    // Act
    final Product result = productServiceImplUnderTest.update(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"), payload);

    // Assert
    assertThat(result).isEqualTo(updatedProduct);
  }


  @Test
  void givenNonExistingProductIdAndPayload_whenUpdate_thenThrowProductNotFoundException() {
    // Arrange
    final Product payload = Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build();
    when(mockProductRepository.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"))).thenReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> productServiceImplUnderTest.update(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"), payload))
        .isInstanceOf(ProductNotFound.class);
  }

  @Test
  void givenProductId_whenFindById_thenReturnProduct() {
    // Arrange
    final Optional<Product> product = Optional.of(Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build());
    when(mockProductRepository.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"))).thenReturn(product);

    // Act
    final Product result = productServiceImplUnderTest.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"));

    // Assert
    assertThat(result).isEqualTo(product.get());
  }


  @Test
  void givenNonExistingProductId_whenFindById_thenThrowProductNotFoundException() {
    // Arrange
    when(mockProductRepository.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8"))).thenReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> productServiceImplUnderTest.findById(
        UUID.fromString("0f143ec1-d466-4c25-93d0-84e9279742b8")))
        .isInstanceOf(ProductNotFound.class);
  }

  @Test
  void givenNoProducts_whenFindAll_thenReturnEmptyList() {
    // Arrange
    when(mockProductRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    final List<Product> result = productServiceImplUnderTest.findAll();

    // Assert
    assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void givenProducts_whenFindAll_thenReturnProductList() {
    // Arrange
    final List<Product> products = List.of(Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build());
    when(mockProductRepository.findAll()).thenReturn(products);

    // Act
    final List<Product> result = productServiceImplUnderTest.findAll();

    // Assert
    assertThat(result).isEqualTo(products);
  }


  @Test
  void givenNoProductsInCategory_whenFindAllByCategory_thenReturnEmptyList() {
    // Arrange
    when(mockProductRepository.findAllByCategory("category")).thenReturn(Collections.emptyList());

    // Act
    final List<Product> result = productServiceImplUnderTest.findAll("category");

    // Assert
    assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void givenProductsInCategory_whenFindAllByCategory_thenReturnProductList() {
    // Arrange
    final List<Product> products = List.of(Product.builder()
        .id(UUID.fromString("9c1fc1a8-45a0-48eb-8b31-35b035cdc83f"))
        .name("name")
        .article(0L)
        .description("description")
        .category("category")
        .price(new BigDecimal("0.00"))
        .build());
    when(mockProductRepository.findAllByCategory("category")).thenReturn(products);

    // Act
    final List<Product> result = productServiceImplUnderTest.findAll("category");

    // Assert
    assertThat(result).isEqualTo(products);
  }
}