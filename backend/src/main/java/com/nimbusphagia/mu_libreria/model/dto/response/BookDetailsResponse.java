package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Book;

public record BookDetailsResponse(
    UUID publicId,
    String title,
    String synopsis,
    AuthorResponse author,
    PublisherResponse publisher,
    List<GenreResponse> genres,
    String isbn,
    Instant createdAt) {
  public static BookDetailsResponse fromEntity(Book book) {
    List<GenreResponse> genreResponses = book.getGenres().stream()
        .map(GenreResponse::fromEntity)
        .toList();

    return new BookDetailsResponse(
        book.getPublicId(),
        book.getTitle(),
        book.getSynopsis(),
        AuthorResponse.fromEntity(book.getAuthor()),
        PublisherResponse.fromEntity(book.getPublisher()),
        genreResponses,
        book.getIsbn(),
        book.getCreatedAt());
  }
}
