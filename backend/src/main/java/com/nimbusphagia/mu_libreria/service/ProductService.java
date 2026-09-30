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
import com.nimbusphagia.mu_libreria.mapper.ProductResponseMapper;
import com.nimbusphagia.mu_libreria.model.dto.page.PageResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.*;
import com.nimbusphagia.mu_libreria.model.dto.product.book.*;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.*;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
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
  private final ProductResponseMapper productResponseMapper;
  private static final Set<String> SORTABLE = Set.of("name", "price", "createdAt");

  // Details
  @Transactional
  public ProductResponse editProduct(UUID productId, EditProductRequest request) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    product.updateFrom(request);
    List<MediaResponse> media = mediaService.getByProduct(productId);
    WorkshopDetailsResponse details = workshopService.getByProduct(productId);
    return productResponseMapper.toResponse(product, null, details, media);
  }

  // Workshops
  @Transactional
  public WorkshopProductResponse createWorkshop(CreateWorkshopRequest request) {
    Product product = productRepository.save(request.toEntity());
    WorkshopDetailsResponse details = workshopService.createAndAttach(product, request.details());
    return WorkshopProductResponse.fromEntity(product, details, List.of());
  }

  public WorkshopProductResponse getWorkshop(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    WorkshopDetailsResponse details = workshopService.getByProduct(productId);
    List<MediaResponse> media = mediaService.getByProduct(product.getPublicId());
    return WorkshopProductResponse.fromEntity(product, details, media);
  }

  public PageResponse<BaseProductResponse> getAllWorkshops(int page, int size, String sortBy) {
    Page<Product> productPage = getPagedProducts(page, size, sortBy, ProductType.WORKSHOP);

    List<UUID> ids = productPage.getContent().stream().map(Product::getPublicId).toList();
    Map<UUID, List<MediaResponse>> mediaByProduct = mediaService.getByProducts(ids);
    Map<UUID, WorkshopStatus> statusByProduct = workshopService.getStatusByProducts(ids);

    return PageResponse.from(productPage.map(product -> {
      UUID id = product.getPublicId();
      List<MediaResponse> media = mediaByProduct.getOrDefault(id, List.of());
      WorkshopStatus status = statusByProduct.get(id);
      return BaseProductResponse.fromEntity(product, media, status);
    }));
  }

  public void deleteWorkshop(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));

    Boolean isAvailable = workshopService.isAvailable(productId);
    if (isAvailable == false)
      throw new BadRequestException("Can't be deleted, only cancelled.");
    // If workshop has registrations(transactions) it can't be deleted, only
    // cancelled
    product.softDelete();
  }

  // Books
  @Transactional
  public BookProductResponse createBook(CreateBookRequest request) {
    Product savedProduct = productRepository.save(request.toEntity());
    BookDetailsResponse details = bookService.createAndAttach(savedProduct, request.details());
    return BookProductResponse.fromEntity(savedProduct, details, List.of());
  }

  public BookProductResponse getBook(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    BookDetailsResponse details = bookService.getByProduct(productId);
    List<MediaResponse> media = mediaService.getByProduct(product.getPublicId());
    return BookProductResponse.fromEntity(product, details, media);
  }

  public void deleteBook(UUID productId) {
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Product not found."));
    product.softDelete();
  }

  public PageResponse<BaseProductResponse> getAllBooks(int page, int size, String sortBy) {
    Page<Product> productPage = getPagedProducts(page, size, sortBy, ProductType.BOOK);

    List<UUID> ids = productPage.getContent().stream().map(Product::getPublicId).toList();
    Map<UUID, List<MediaResponse>> mediaByProduct = mediaService.getByProducts(ids);

    return PageResponse.from(productPage.map(product -> {
      UUID id = product.getPublicId();
      List<MediaResponse> media = mediaByProduct.getOrDefault(id, List.of());
      return BaseProductResponse.fromEntity(product, media, null);
    }));
  }

  // Media
  @Transactional
  public BaseProductResponse addMedia(UUID productId, List<MediaRequest> request) {
    if (request.size() > 10)
      throw new BadRequestException("Limited to 10 uploads per request.");
    Product product = productRepository.findByPublicId(productId)
        .orElseThrow(() -> new BadRequestException("Invalid product ID"));
    List<MediaResponse> media = request.stream()
        .map((item) -> mediaService.createAndAttachToProduct(item, product))
        .toList();
    WorkshopStatus workshopStatus = workshopService.getStatusByProduct(productId);
    return BaseProductResponse.fromEntity(product, media, workshopStatus);
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
}
