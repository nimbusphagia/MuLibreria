package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

public record ProductResponse(
    UUID publicId,
    String name,
    String description,
    BigDecimal price,
    String imageUrl,
    String sku,
    Integer totalStock,
    Integer availableStock,
    ProductType productType,
    BookResponse book,
    WorkshopResponse workshop,
    Instant createdAt) {

  public static ProductResponse fromEntity(Product product, Book book, Workshop workshop) {
    return new ProductResponse(
        product.getPublicId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImageUrl(),
        product.getSku(),
        product.getTotalStock(),
        product.getAvailableStock(),
        product.getType(),
        book != null ? BookResponse.fromEntity(book) : null,
        workshop != null ? WorkshopResponse.fromEntity(workshop) : null,
        product.getCreatedAt());
  }
}
