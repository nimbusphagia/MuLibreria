package com.nimbusphagia.mu_libreria.model.dto.response;

import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Facilitator;

public record FacilitatorResponse(
    UUID publicId,
    String name,
    String lastName) {
  public static FacilitatorResponse fromEntity(Facilitator facilitator) {
    return new FacilitatorResponse(facilitator.getPublicId(), facilitator.getName(), facilitator.getLastName());
  }
}
