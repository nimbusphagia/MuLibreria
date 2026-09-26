package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Product;

public record BookProductResponse(
    UUID productId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    BookDetailsResponse details,
    String sku,
    Integer totalStock,
    Integer availableStock,
    Instant createdAt) {

  public static BookProductResponse fromEntity(Product product, BookDetailsResponse details,
      List<MediaResponse> media) {
    return new BookProductResponse(
        product.getPublicId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        media,
        details,
        product.getSku(),
        product.getTotalStock(),
        product.getAvailableStock(),
        product.getCreatedAt());
  }
}
