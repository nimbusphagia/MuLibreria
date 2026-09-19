package com.nimbusphagia.mu_libreria.service;

import java.util.UUID;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.ProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.PageResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductItemService productItemService;
  private final ProductRepository productRepository;
  private static final Set<String> SORTABLE = Set.of("name", "price", "createdAt");

  public ProductResponse createProduct(ProductRequest productRequest) {
    return productItemService.createProduct(productRequest);
  }

  @Transactional
  public ProductSummaryResponse editProduct(UUID publicId, ProductRequest productRequest) {
    Product existingProduct = productRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    existingProduct.updateFrom(productRequest);
    productRepository.save(existingProduct);
    return ProductSummaryResponse.fromEntity(existingProduct);
  }

  @Transactional
  public void softDelete(UUID publicId) {
    Product existingProduct = productRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    existingProduct.softDelete();
    productRepository.save(existingProduct);
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

    return PageResponse.from(products.map(ProductSummaryResponse::fromEntity));
  }
}
