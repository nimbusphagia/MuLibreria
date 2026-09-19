package com.nimbusphagia.mu_libreria.service;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.BookResponse;
import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;
import com.nimbusphagia.mu_libreria.repository.AuthorRepository;
import com.nimbusphagia.mu_libreria.repository.BookRepository;
import com.nimbusphagia.mu_libreria.repository.GenreRepository;
import com.nimbusphagia.mu_libreria.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final PublisherRepository publisherRepository;
  private final GenreRepository genreRepository;

  @Transactional
  public BookResponse editBook(UUID publicId, BookDetailsRequest details) {

    Book existingBook = bookRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found."));

    Author author = authorRepository.findByPublicId(details.authorId())
        .orElseThrow(() -> new ResourceNotFoundException("Invalid author"));

    Publisher publisher = publisherRepository.findByPublicId(details.publisherId())
        .orElseThrow(() -> new ResourceNotFoundException("Invalid publisher"));

    Set<Genre> genres = new HashSet<>();
    if (details.genreIds() != null && !details.genreIds().isEmpty()) {
      List<Genre> foundGenres = genreRepository.findAllByPublicIdIn(details.genreIds());
      if (foundGenres.size() != details.genreIds().size()) {
        throw new ResourceNotFoundException("One or more genres not found.");
      }
      genres.addAll(foundGenres);
    }

    existingBook.updateFrom(details, author, publisher, genres);
    return BookResponse.fromEntity(bookRepository.save(existingBook));
  }
}
