package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

public record ProductSummaryResponse(
    UUID publicId,
    String name,
    BigDecimal price,
    String imageUrl,
    ProductType productType,
    boolean inStock) {

  public static ProductSummaryResponse fromEntity(Product product) {
    return new ProductSummaryResponse(
        product.getPublicId(),
        product.getName(),
        product.getPrice(),
        product.getImageUrl(),
        product.getType(),
        product.getAvailableStock() != null && product.getAvailableStock() > 0);
  }
}
