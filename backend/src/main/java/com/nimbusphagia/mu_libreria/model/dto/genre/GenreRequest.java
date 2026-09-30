package com.nimbusphagia.mu_libreria.model.dto.genre;

import com.nimbusphagia.mu_libreria.model.entity.Genre;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
    @NotBlank(message = "Name is required") String name) {
  public Genre toEntity() {
    return Genre.builder()
        .name(this.name())
        .build();
  }
}
