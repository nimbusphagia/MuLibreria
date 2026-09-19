package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

public record WorkshopResponse(
    UUID publicId,
    String title,
    String description,
    List<String> images,
    FacilitatorResponse facilitator,
    LocalDateTime datetime,
    Integer durationMinutes,
    String location,
    Integer capacity,
    WorkshopStatus status,
    Instant createdAt) {

  public static WorkshopResponse fromEntity(Workshop workshop) {
    return new WorkshopResponse(
        workshop.getPublicId(),
        workshop.getTitle(),
        workshop.getDescription(),
        workshop.getImages(),
        workshop.getFacilitator() != null ? FacilitatorResponse.fromEntity(workshop.getFacilitator()) : null,
        workshop.getDatetime(),
        workshop.getDurationMinutes(),
        workshop.getLocation(),
        workshop.getCapacity(),
        workshop.getStatus(),
        workshop.getCreatedAt());
  }
}
