package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Product;

public record WorkshopProductResponse(
    UUID productId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    String sku,
    WorkshopDetailsResponse details,
    Instant createdAt) {

  public static WorkshopProductResponse fromEntity(Product product, WorkshopDetailsResponse details,
      List<MediaResponse> media) {
    return new WorkshopProductResponse(
        product.getPublicId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        media,
        product.getSku(),
        details,
        product.getCreatedAt());
  }
}
