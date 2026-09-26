package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

public record WorkshopDetailsResponse(
    UUID publicId,
    String title,
    String description,
    FacilitatorResponse facilitator,
    LocalDateTime datetime,
    Integer durationMinutes,
    String location,
    Integer capacity,
    WorkshopStatus status,
    Instant createdAt) {

  public static WorkshopDetailsResponse fromEntity(Workshop workshop) {
    return new WorkshopDetailsResponse(
        workshop.getPublicId(),
        workshop.getTitle(),
        workshop.getDescription(),
        workshop.getFacilitator() != null ? FacilitatorResponse.fromEntity(workshop.getFacilitator()) : null,
        workshop.getDatetime(),
        workshop.getDurationMinutes(),
        workshop.getLocation(),
        workshop.getCapacity(),
        workshop.getStatus(),
        workshop.getCreatedAt());
  }
}
