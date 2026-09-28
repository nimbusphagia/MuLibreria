package com.nimbusphagia.mu_libreria.model.dto.publisher;

import com.nimbusphagia.mu_libreria.model.entity.Publisher;

import jakarta.validation.constraints.NotNull;

public record PublisherRequest(
    @NotNull(message = "Name is required") String name) {
  public Publisher toEntity() {
    return Publisher.builder()
        .name(this.name())
        .build();
  }
}
