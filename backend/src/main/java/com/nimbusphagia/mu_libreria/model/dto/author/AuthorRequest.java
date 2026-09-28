package com.nimbusphagia.mu_libreria.model.dto.author;

import com.nimbusphagia.mu_libreria.model.entity.Author;

import jakarta.validation.constraints.NotNull;

public record AuthorRequest(
    @NotNull(message = "Name is required") String name,
    String lastName) {
  public Author toEntity() {
    return Author.builder()
        .name(this.name())
        .lastName(this.lastName())
        .build();
  }
}
