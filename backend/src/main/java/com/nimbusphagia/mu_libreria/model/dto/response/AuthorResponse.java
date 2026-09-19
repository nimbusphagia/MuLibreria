package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Author;

public record AuthorResponse(
    UUID publicId,
    String name,
    String lastName,
    Instant createdAt) {
  public static AuthorResponse fromEntity(Author author) {
    return new AuthorResponse(author.getPublicId(), author.getName(), author.getLastName(), author.getCreatedAt());
  }
}
