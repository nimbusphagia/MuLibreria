package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.mapper.ProductAvailabilityMapper;

public record BaseProductResponse(
    UUID publicId,
    String name,
    BigDecimal price,
    String sku,
    ProductType productType,
    Availability availability,
    List<MediaResponse> media,
    Instant createdAt) {

  public static BaseProductResponse fromEntity(
      Product p,
      List<MediaResponse> media,
      WorkshopStatus workshopStatus) {
    Availability availability = ProductAvailabilityMapper.fromProduct(p, workshopStatus);
    return new BaseProductResponse(
        p.getPublicId(), p.getName(), p.getPrice(), p.getSku(),
        p.getType(), availability, media, p.getCreatedAt());
  }
}
