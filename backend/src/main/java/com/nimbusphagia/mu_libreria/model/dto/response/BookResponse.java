package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Book;

public record BookResponse(
    UUID publicId,
    String title,
    String synopsis,
    List<String> images,
    AuthorResponse author,
    PublisherResponse publisher,
    List<GenreResponse> genres,
    String isbn,
    Instant createdAt) {
  public static BookResponse fromEntity(Book book) {
    List<GenreResponse> genreResponses = book.getGenres().stream()
        .map(GenreResponse::fromEntity)
        .toList();

    return new BookResponse(
        book.getPublicId(),
        book.getTitle(),
        book.getSynopsis(),
        book.getImages(),
        AuthorResponse.fromEntity(book.getAuthor()),
        PublisherResponse.fromEntity(book.getPublisher()),
        genreResponses,
        book.getIsbn(),
        book.getCreatedAt());
  }
}
