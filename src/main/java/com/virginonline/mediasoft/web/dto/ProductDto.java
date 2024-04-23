package com.virginonline.mediasoft.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

public enum ProductDto {
  ;

  public enum Request {
    ;

    @Builder
    @Schema(name = "Create request", description = "Product create request")
    public record Create(
        @Nonnull @NotBlank @Schema(description = "Product name") String name,
        @Nonnull @Schema(description = "Product article") Long article,
        @Nonnull @NotBlank @Schema(description = "Product description") String description,
        @Nonnull @NotBlank @Schema(description = "Product description") String category,
        @Nonnull @Min(1) @Schema(description = "Product price") BigDecimal price,
        @Nonnull @Min(1) @Schema(description = "Product quantity") Long quantity) {}

    @Builder
    @Schema(name = "Patch request", description = "Product patch request")
    public record Patch(
        @Nonnull @NotBlank @Schema(description = "Product name") String name,
        @Nonnull @Schema(description = "Product article") Long article,
        @Nonnull @NotBlank @Schema(description = "Product description") String description,
        @Nonnull @NotBlank @Schema(description = "Product description") String category,
        @Nonnull @Min(1) @Schema(description = "Product price") BigDecimal price,
        @Nonnull @Min(1) @Schema(description = "Product quantity") Long quantity) {}
  }

  public enum Response {
    ;

    @Builder
    @Schema(name = "Product dto", description = "Default product response")
    public record Default(
        @Nonnull
            @NotBlank
            @Schema(
                description = "Product id",
                format = "uuid",
                example = "0f143ec1-d466-4c25-93d0-84e9279742b8")
            UUID id,
        @Nonnull @NotBlank @Schema(description = "Product name") String name,
        @Nonnull @Schema(description = "Product article") Long article,
        @Nonnull @NotBlank @Schema(description = "Product description") String description,
        @Nonnull @NotBlank @Schema(description = "Product description") String category,
        @Nonnull @Min(1) @Schema(description = "Product price") BigDecimal price,
        @Nonnull @Min(1) @Schema(description = "Product quantity") Long quantity) {}
  }
}
