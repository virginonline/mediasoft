package com.virginonline.mediasoft.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "Product dto")
@JsonNaming(SnakeCaseStrategy.class)
public record ProductDto(
    @Schema(description = "product id", format = "uuid", example = "0f143ec1-d466-4c25-93d0-84e9279742b8") UUID id,
    @Nonnull @Schema(description = "product name", example = "product example") String name,
    @Nonnull @Schema(description = "product article", example = "123131231") Long article,
    @Nonnull @Schema(description = "product category", example = "category") String category,
    @Schema(description = "product description", example = "product example description") String description,
    @Nonnull @Min(1) @Schema(description = "product price", example = "1") BigDecimal price) implements
    Serializable {

}