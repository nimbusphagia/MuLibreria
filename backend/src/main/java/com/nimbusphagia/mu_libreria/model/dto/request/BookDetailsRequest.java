package com.nimbusphagia.mu_libreria.model.dto.request;

import java.util.Set;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDetailsRequest(
    @NotBlank String title,
    String synopsis,
    @NotNull(message = "Author id is required") UUID authorId,
    @NotBlank String isbn,
    @NotNull(message = "Publisher id is required") UUID publisherId,
    Set<UUID> genreIds) {

  public Book buildBook(Book book, Product product) {
    return Book.builder()
        .title(this.title)
        .synopsis(this.synopsis)
        .isbn(this.isbn)
        .author(book.getAuthor())
        .publisher(book.getPublisher())
        .genres(book.getGenres())
        .product(product)
        .build();
  }
}
