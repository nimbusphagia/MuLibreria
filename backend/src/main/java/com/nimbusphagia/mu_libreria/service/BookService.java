package com.nimbusphagia.mu_libreria.service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;
import com.nimbusphagia.mu_libreria.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorService authorService;
  private final PublisherService publisherService;
  private final GenreService genreService;

  // GET
  public Book getByProduct(UUID productId) {
    return bookRepository.findByProduct_PublicId(productId);
  }

  public Map<UUID, Book> getByProducts(List<UUID> productIds) {
    return bookRepository.findAllByProduct_PublicIdIn(productIds).stream()
        .collect(Collectors.toMap(b -> b.getProduct().getPublicId(), b -> b));
  }

  // EDIT
  @Transactional
  public BookDetailsResponse editBook(UUID publicId, BookDetailsRequest request) {
    Book existingBook = bookRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found."));

    Author author = authorService.getByPublicId(request.authorId());
    Publisher publisher = publisherService.getByPublicId(request.publisherId());
    Set<Genre> genres = genreService.resolveGenres(request.genreIds());

    existingBook.updateFrom(request, author, publisher, genres);
    return BookDetailsResponse.fromEntity(existingBook);
  }

  // For Product Creation
  @Transactional
  public Book createAndAttach(Product product, BookDetailsRequest request) {
    if (request == null) {
      throw new BadRequestException("Book details are required for products.");
    }

    Author author = authorService.getByPublicId(request.authorId());
    Publisher publisher = publisherService.getByPublicId(request.publisherId());
    Set<Genre> genres = genreService.resolveGenres(request.genreIds());

    Book book = Book.from(request, author, publisher, genres, product);

    return bookRepository.save(book);
  }
}
