package com.nimbusphagia.mu_libreria.model.dto.response;

import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Author;

public record AuthorResponse(
    UUID publicId,
    String name,
    String lastName) {
  public static AuthorResponse fromEntity(Author author) {
    return new AuthorResponse(author.getPublicId(), author.getName(), author.getLastName());
  }
}
