package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Publisher;

public record PublisherResponse(
    UUID publicId,
    String name,
    Instant createdAt) {
  public static PublisherResponse fromEntity(Publisher publisher) {
    return new PublisherResponse(publisher.getPublicId(), publisher.getName(), publisher.getCreatedAt());
  }
}
