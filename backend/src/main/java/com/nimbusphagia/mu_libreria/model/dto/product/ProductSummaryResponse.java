package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

public record ProductSummaryResponse(
    UUID publicId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    ProductType productType,
    Instant createdAt,
    Boolean inStock) {

  public static ProductSummaryResponse fromEntity(Product product, List<MediaResponse> media, Boolean available) {
    return new ProductSummaryResponse(
        product.getPublicId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        media,
        product.getType(),
        product.getCreatedAt(),
        available);
  }
}
