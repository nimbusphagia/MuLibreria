package com.nimbusphagia.mu_libreria.model.dto.request;

import java.math.BigDecimal;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotBlank String name,
    String description,
    @NotNull BigDecimal price,
    String imageUrl,
    String sku,
    @NotNull ProductType type,
    Integer totalStock,
    Integer availableStock,
    BookDetailsRequest bookDetails,
    WorkshopDetailsRequest workshopDetails) {
  public Product toEntity() {
    return Product.builder()
        .name(this.name)
        .description(this.description)
        .price(this.price)
        .imageUrl(this.imageUrl)
        .sku(this.sku)
        .totalStock(this.totalStock)
        .availableStock(this.availableStock)
        .type(this.type)
        .build();
  }
}
