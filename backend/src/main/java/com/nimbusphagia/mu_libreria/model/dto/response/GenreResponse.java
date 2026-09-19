package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Genre;

public record GenreResponse(
    UUID publicId,
    String name,
    Instant createdAt) {
  public static GenreResponse fromEntity(Genre genre) {
    return new GenreResponse(genre.getPublicId(), genre.getName(), genre.getCreatedAt());
  }

}
