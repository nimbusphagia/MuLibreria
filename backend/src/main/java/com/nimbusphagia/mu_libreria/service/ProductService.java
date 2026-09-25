package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.BookRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.MediaResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.PageResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final BookService bookService;
  private final WorkshopService workshopService;
  private final ProductMediaService mediaService;
  private static final Set<String> SORTABLE = Set.of("name", "price", "createdAt");

  // GET
  public ProductResponse getProduct(UUID publicId) {
    Product product = productRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    Book book = null;
    Workshop workshop = null;
    switch (product.getType()) {
      case BOOK -> book = bookService.getByProduct(product.getPublicId());
      case WORKSHOP -> workshop = workshopService.getByProduct(product.getPublicId());
      case MERCH -> {
      }
    }
    List<MediaResponse> media = mediaService.getByProduct(publicId);
    return ProductResponse.fromEntity(product, book, workshop, media);
  }

  public PageResponse<ProductSummaryResponse> getProducts(int page, int size, String sortBy, ProductType filter) {
    if (!SORTABLE.contains(sortBy)) {
      throw new BadRequestException("Invalid sort field: " + sortBy);
    }
    int safeSize = Math.min(Math.max(size, 1), 50);
    Pageable pageable = PageRequest.of(Math.max(page, 0), safeSize, Sort.by(sortBy));
    Page<Product> products = (filter == null)
        ? productRepository.findAll(pageable)
        : productRepository.findAllByType(filter, pageable);

    return PageResponse.from(products.map((product) -> {
      List<MediaResponse> media = mediaService.getByProduct(product.getPublicId());
      return ProductSummaryResponse.fromEntity(product, media);
    }));
  }

  // Details
  @Transactional
  public ProductSummaryResponse editProductDetails(UUID publicId, BookRequest bookRequest,
      WorkshopRequest workshopRequest) {
    Product existingProduct = productRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    switch (existingProduct.getType()) {
      case BOOK:
        existingProduct.updateFrom(bookRequest, null);
        break;
      case WORKSHOP:
        existingProduct.updateFrom(null, workshopRequest);
        break;
      default:
        break;
    }
    List<MediaResponse> productMedia = mediaService.getByProduct(publicId);
    return ProductSummaryResponse.fromEntity(existingProduct, productMedia);
  }

  // Workshops
  @Transactional
  public ProductResponse createWorkshop(WorkshopRequest request) {
    Product savedProduct = productRepository.save(request.toEntity());
    Workshop workshop = workshopService.createAndAttach(savedProduct, request.details());
    return ProductResponse.fromEntity(savedProduct, null, workshop, List.of());
  }

  public void deleteWorkshop(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

    Workshop workshop = workshopService.getByProduct(productId);
    if (workshop.getStatus() != WorkshopStatus.REGISTRATION_OPEN)
      throw new BadRequestException("Can't be deleted, only cancelled.");
    // If workshop has registrations(transactions) it can't be deleted, only
    // cancelled
    product.softDelete();
  }

  // Books
  @Transactional
  public ProductResponse createBook(BookRequest request) {
    Product savedProduct = productRepository.save(request.toEntity());
    Book book = bookService.createAndAttach(savedProduct, request.details());
    return ProductResponse.fromEntity(savedProduct, book, null, List.of());
  }

  public void deleteBook(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    product.softDelete();
  }

  // Media

  // Utilities
  public Boolean exists(UUID productId) {
    return productRepository.existsByPublicId(productId);
  }
}
