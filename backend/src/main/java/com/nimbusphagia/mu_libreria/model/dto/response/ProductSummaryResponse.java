package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

public record ProductSummaryResponse(
    UUID publicId,
    String name,
    BigDecimal price,
    List<MediaResponse> media,
    ProductType productType,
    boolean inStock) {

  public static ProductSummaryResponse fromEntity(Product product, List<MediaResponse> media) {
    return new ProductSummaryResponse(
        product.getPublicId(),
        product.getName(),
        product.getPrice(),
        media,
        product.getType(),
        product.getAvailableStock() != null && product.getAvailableStock() > 0);
  }
}
