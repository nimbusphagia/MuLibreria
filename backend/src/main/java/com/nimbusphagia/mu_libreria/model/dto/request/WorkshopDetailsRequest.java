package com.nimbusphagia.mu_libreria.model.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkshopDetailsRequest(
    @NotBlank String title,
    @NotBlank String description,
    @NotNull UUID facilitatorId,
    @NotNull(message = "Datetime is required") LocalDateTime datetime,
    @NotNull(message = "Duration is required") Integer durationMinutes,
    @NotBlank(message = "Location is required") String location,
    @NotNull(message = "Capacity is required") Integer capacity,
    WorkshopStatus status) {

  public Workshop toEntity(Product product, Facilitator facilitator) {
    return Workshop.builder()
        .product(product)
        .facilitator(facilitator)
        .title(this.title)
        .description(this.description)
        .datetime(this.datetime)
        .durationMinutes(this.durationMinutes)
        .location(this.location)
        .capacity(this.capacity)
        .status(this.status == null ? WorkshopStatus.REGISTRATION_OPEN : this.status)
        .build();
  }
}
