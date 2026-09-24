package com.nimbusphagia.mu_libreria.model.dto.request;

import java.util.Set;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDetailsRequest(
    @NotBlank String title,
    String synopsis,
    @NotNull(message = "Author id is required") UUID authorId,
    @NotBlank String isbn,
    @NotNull(message = "Publisher id is required") UUID publisherId,
    Set<UUID> genreIds) {

  public Book toEntity(Product product, Author author, Publisher publisher, Set<Genre> genres) {
    return Book.builder()
        .product(product)
        .title(this.title)
        .synopsis(this.synopsis)
        .author(author)
        .isbn(this.isbn)
        .publisher(publisher)
        .genres(genres)
        .build();
  }
}
