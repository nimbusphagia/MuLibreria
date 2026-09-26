package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.model.dto.request.BookRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.MediaRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.BookProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.MediaResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.PageResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.WorkshopProductResponse;
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

  // Details
  @Transactional
  public ProductSummaryResponse editProductDetails(UUID publicId, BookRequest bookRequest,
      WorkshopRequest workshopRequest) {
    Product existingProduct = productRepository.findByPublicId(publicId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    switch (existingProduct.getType()) {
      case BOOK -> existingProduct.updateFrom(bookRequest, null);
      case WORKSHOP -> existingProduct.updateFrom(null, workshopRequest);
      default -> {
      }
    }
    List<MediaResponse> productMedia = mediaService.getByProduct(publicId);
    return ProductSummaryResponse.fromEntity(existingProduct, productMedia, false);
  }

  // Workshops
  @Transactional
  public ProductResponse createWorkshop(WorkshopRequest request) {
    Product savedProduct = productRepository.save(request.toEntity());
    Workshop workshop = workshopService.createAndAttach(savedProduct, request.details());
    return ProductResponse.fromEntity(savedProduct, null, workshop, List.of());
  }

  public WorkshopProductResponse getWorkshop(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    Workshop workshop = workshopService.getByProduct(productId);
    List<MediaResponse> media = mediaService.getByProduct(product.getPublicId());
    return WorkshopProductResponse.fromEntity(product, WorkshopDetailsResponse.fromEntity(workshop), media);
  }

  public PageResponse<WorkshopProductResponse> getAllWorkshops(int page, int size, String sortBy) {
    Page<Product> productPage = getPagedProducts(page, size, sortBy, ProductType.WORKSHOP);

    List<UUID> ids = productPage.getContent().stream().map(Product::getPublicId).toList();
    Map<UUID, List<MediaResponse>> mediaByProduct = mediaService.getByProducts(ids);
    Map<UUID, Workshop> workshopByProduct = workshopService.getByProducts(ids);

    return PageResponse.from(productPage.map(product -> {
      UUID id = product.getPublicId();
      Workshop workshop = workshopByProduct.get(id);
      List<MediaResponse> media = mediaByProduct.getOrDefault(id, List.of());
      return WorkshopProductResponse.fromEntity(product, WorkshopDetailsResponse.fromEntity(workshop), media);
    }));
  }

  public void deleteWorkshop(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));

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

  public BookProductResponse getBook(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    Book book = bookService.getByProduct(productId);
    List<MediaResponse> media = mediaService.getByProduct(product.getPublicId());
    return BookProductResponse.fromEntity(product, BookDetailsResponse.fromEntity(book), media);
  }

  public void deleteBook(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    product.softDelete();
  }

  public PageResponse<BookProductResponse> getAllBooks(int page, int size, String sortBy) {
    Page<Product> productPage = getPagedProducts(page, size, sortBy, ProductType.BOOK);

    List<UUID> ids = productPage.getContent().stream().map(Product::getPublicId).toList();
    Map<UUID, List<MediaResponse>> mediaByProduct = mediaService.getByProducts(ids);
    Map<UUID, Book> bookByProduct = bookService.getByProducts(ids);

    return PageResponse.from(productPage.map(product -> {
      UUID id = product.getPublicId();
      Book book = bookByProduct.get(id);
      List<MediaResponse> media = mediaByProduct.getOrDefault(id, List.of());
      return BookProductResponse.fromEntity(product, BookDetailsResponse.fromEntity(book), media);
    }));
  }

  // Media
  @Transactional
  public ProductResponse addMedia(UUID productId, List<MediaRequest> request) {
    if (request.size() > 10)
      throw new BadRequestException("Limited to 10 uploads per request.");
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Invalid product ID"));
    List<MediaResponse> media = request.stream()
        .map((item) -> mediaService.createAndAttachToProduct(item, product))
        .toList();
    return buildResponseByType(product, null, null, media);
  }

  // Utilities
  private Page<Product> getPagedProducts(int page, int size, String sortBy, ProductType filter) {
    if (!SORTABLE.contains(sortBy))
      throw new BadRequestException("Invalid sort field: " + sortBy);
    int safeSize = Math.min(Math.max(size, 1), 50);
    Pageable pageable = PageRequest.of(Math.max(page, 0), safeSize, Sort.by(sortBy));
    return (filter == null)
        ? productRepository.findAll(pageable)
        : productRepository.findAllByType(filter, pageable);
  }

  private ProductResponse buildResponseByType(
      Product product,
      Book book, Workshop workshop,
      List<MediaResponse> media) {
    Book savedBook = null;
    Workshop savedWorkshop = null;
    switch (product.getType()) {
      case BOOK -> savedBook = book != null ? book : bookService.getByProduct(product.getPublicId());
      case WORKSHOP ->
        savedWorkshop = workshop != null ? workshop : workshopService.getByProduct(product.getPublicId());
      default -> {
      }
    }
    List<MediaResponse> savedMedia = media != null ? media : mediaService.getByProduct(product.getPublicId());
    return ProductResponse.fromEntity(product, savedBook, savedWorkshop, savedMedia);
  }
}
