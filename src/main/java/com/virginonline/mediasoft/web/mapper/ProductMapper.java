package com.virginonline.mediasoft.web.mapper;

import com.virginonline.mediasoft.domain.Product;
import com.virginonline.mediasoft.web.dto.ProductDto;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

  public static Product toEntity(ProductDto dto) {
    return Product.builder()
        .id(dto.id())
        .name(dto.name())
        .category(dto.category())
        .description(dto.description())
        .article(dto.article())
        .price(dto.price())
        .build();
  }

  public static ProductDto toDto(Product product) {
    return new ProductDto(product.getId(), product.getName(), product.getArticle(),
        product.getCategory(), product.getDescription(),product.getQuantity(), product.getPrice());
  }

  public static List<Product> toEntity(List<ProductDto> dtos) {
    return dtos.stream().map(ProductMapper::toEntity).toList();
  }

  public static List<ProductDto> toDto(List<Product> entities) {
    return entities.stream().map(ProductMapper::toDto).toList();
  }
}
