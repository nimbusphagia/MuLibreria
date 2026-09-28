package com.nimbusphagia.mu_libreria.model.dto.facilitator;

import com.nimbusphagia.mu_libreria.model.entity.Facilitator;

import jakarta.validation.constraints.NotNull;

public record FacilitatorRequest(
    @NotNull(message = "Name is required") String name,
    String lastName) {
  public Facilitator toEntity() {
    return Facilitator.builder()
        .name(this.name())
        .lastName(this.lastName())
        .build();
  }
}
