package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.model.enums.MediaProvider;
import com.nimbusphagia.mu_libreria.model.enums.ProductMediaType;

public record MediaResponse(
    UUID publicId,
    String url,
    MediaProvider provider,
    ProductMediaType type,
    String altText,
    int position,
    Instant createdAt) {
  public static MediaResponse fromEntity(ProductMedia entity) {
    return new MediaResponse(
        entity.getPublicId(),
        entity.getUrl(),
        entity.getProvider(),
        entity.getType(),
        entity.getAltText(),
        entity.getPosition(),
        entity.getCreatedAt());
  }
}
