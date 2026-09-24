package com.nimbusphagia.mu_libreria.model.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
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
    List<MediaResponse> media,
    String sku,
    Integer totalStock,
    Integer availableStock,
    ProductType productType,
    BookResponse book,
    WorkshopResponse workshop,
    Instant createdAt) {

  public static ProductResponse fromEntity(Product product, Book book, Workshop workshop, List<MediaResponse> media) {
    return new ProductResponse(
        product.getPublicId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        media,
        product.getSku(),
        product.getTotalStock(),
        product.getAvailableStock(),
        product.getType(),
        book != null ? BookResponse.fromEntity(book) : null,
        workshop != null ? WorkshopResponse.fromEntity(workshop) : null,
        product.getCreatedAt());
  }
}
