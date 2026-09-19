package com.nimbusphagia.mu_libreria.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.ProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductItemService productItemService;
  private final ProductRepository productRepository;

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
}
