package com.virginonline.mediasoft.web.mapper;

import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.web.dto.ProductDto;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

  public static Product toEntity(ProductDto.Request.Create dto) {
    return Product.builder()
        .name(dto.name())
        .category(dto.category())
        .description(dto.description())
        .article(dto.article())
        .price(dto.price())
        .quantity(dto.quantity())
        .build();
  }

  public static ProductDto.Response.Default toDto(Product product) {

    return ProductDto.Response.Default.builder()
        .id(product.getId())
        .name(product.getName())
        .article(product.getArticle())
        .category(product.getCategory())
        .quantity(product.getQuantity())
        .price(product.getPrice())
        .description(product.getDescription())
        .build();
  }

  public static List<ProductDto.Response.Default> toDto(List<Product> products) {
    return products.stream().map(ProductMapper::toDto).toList();
  }
}
