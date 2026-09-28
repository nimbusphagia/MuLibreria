package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

public record BookProductResponse(
    UUID publicId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    String sku,
    ProductType productType,
    Availability availability,
    Instant createdAt,
    Integer totalStock,
    Integer availableStock) implements ProductResponse {

  private static final int LOW_STOCK_THRESHOLD = 3;

  public static BookProductResponse fromEntity(Product p, List<MediaResponse> media) {
    int available = p.getAvailableStock();
    Availability availability = available <= 0 ? Availability.SOLD_OUT
        : available <= LOW_STOCK_THRESHOLD ? Availability.LOW_STOCK
            : Availability.IN_STOCK;

    return new BookProductResponse(
        p.getPublicId(), p.getName(), p.getDescription(), p.getPrice(), media,
        p.getSku(), p.getType(), availability, p.getCreatedAt(),
        p.getTotalStock(), available);
  }
}
