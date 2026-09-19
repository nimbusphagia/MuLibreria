package com.nimbusphagia.mu_libreria.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.ProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.repository.AuthorRepository;
import com.nimbusphagia.mu_libreria.repository.BookRepository;
import com.nimbusphagia.mu_libreria.repository.FacilitatorRepository;
import com.nimbusphagia.mu_libreria.repository.GenreRepository;
import com.nimbusphagia.mu_libreria.repository.ProductRepository;
import com.nimbusphagia.mu_libreria.repository.PublisherRepository;
import com.nimbusphagia.mu_libreria.repository.WorkshopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCreateService {

  private final ProductRepository productRepository;
  private final BookRepository bookRepository;
  private final WorkshopRepository workshopRepository;
  private final AuthorRepository authorRepository;
  private final PublisherRepository publisherRepository;
  private final GenreRepository genreRepository;
  private final FacilitatorRepository facilitatorRepository;

  @Transactional
  public ProductResponse createProduct(ProductRequest request) {
    if (request.type() == ProductType.WORKSHOP &&
        (request.totalStock() != null || request.availableStock() != null)) {
      throw new IllegalArgumentException("Workshops should not have stock values; use capacity instead.");
    }
    Product savedProduct = productRepository.save(request.toEntity());

    Book book = null;
    Workshop workshop = null;

    switch (request.type()) {
      case BOOK -> book = createBookDetails(savedProduct, request.bookDetails());
      case WORKSHOP -> workshop = createWorkshopDetails(savedProduct, request.workshopDetails());
      case MERCH -> {
      }
    }

    return ProductResponse.fromEntity(savedProduct, book, workshop);
  }

  private Book createBookDetails(Product product, BookDetailsRequest details) {
    if (details == null) {
      throw new IllegalArgumentException("Book details are required for BOOK products.");
    }

    Author author = authorRepository.findById(details.authorId())
        .orElseThrow(() -> new ResourceNotFoundException("Author not found."));
    Publisher publisher = publisherRepository.findById(details.publisherId())
        .orElseThrow(() -> new ResourceNotFoundException("Publisher not found."));

    Set<Genre> genres = new HashSet<>();
    if (details.genreIds() != null && !details.genreIds().isEmpty()) {
      List<Genre> found = genreRepository.findAllById(details.genreIds());
      if (found.size() != details.genreIds().size()) {
        throw new ResourceNotFoundException("One or more genres not found.");
      }
      genres.addAll(found);
    }

    Book book = details.toEntity(product, author, publisher, genres);
    return bookRepository.save(book);
  }

  private Workshop createWorkshopDetails(Product product, WorkshopDetailsRequest details) {
    if (details == null) {
      throw new IllegalArgumentException("Workshop details are required for WORKSHOP products.");
    }

    Facilitator facilitator = facilitatorRepository.findById(details.facilitatorId())
        .orElseThrow(() -> new ResourceNotFoundException("Facilitator not found."));

    Workshop workshop = details.toEntity(product, facilitator);
    return workshopRepository.save(workshop);
  }
}
