package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.mapper.ProductAvailabilityMapper;

public record WorkshopProductResponse(
    UUID publicId,
    String name,
    String description,
    BigDecimal price,
    List<MediaResponse> media,
    WorkshopDetailsResponse details,
    String sku,
    ProductType productType,
    Availability availability,
    Instant createdAt) implements ProductResponse {

  public static WorkshopProductResponse fromEntity(Product p, WorkshopDetailsResponse details,
      List<MediaResponse> media) {
    Availability availability = ProductAvailabilityMapper.fromStatus(details.status());
    return new WorkshopProductResponse(
        p.getPublicId(), p.getName(), p.getDescription(), p.getPrice(), media, details,
        p.getSku(), p.getType(), availability, p.getCreatedAt());
  }
}
