package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.dto.product.book.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.mapper.ProductAvailabilityMapper;

public record BookProductResponse(
    UUID publicId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    BookDetailsResponse details,
    String sku,
    ProductType productType,
    Availability availability,
    Instant createdAt,
    Integer totalStock,
    Integer availableStock) implements ProductResponse {

  public static BookProductResponse fromEntity(Product p, BookDetailsResponse details, List<MediaResponse> media) {
    int available = p.getAvailableStock();
    Availability availability = ProductAvailabilityMapper
        .fromStock(p.getAvailableStock());

    return new BookProductResponse(
        p.getPublicId(), p.getName(), p.getDescription(), p.getPrice(), media, details,
        p.getSku(), p.getType(), availability, p.getCreatedAt(),
        p.getTotalStock(), available);
  }
}
