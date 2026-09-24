package com.nimbusphagia.mu_libreria.model.dto.request;

import java.math.BigDecimal;

import com.nimbusphagia.mu_libreria.model.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
    @NotBlank String name,
    String description,
    @NotNull BigDecimal price,
    String sku,
    Integer totalStock,
    Integer availableStock,
    BookDetailsRequest details) {
  public Product toEntity() {
    return Product.builder()
        .name(this.name)
        .description(this.description)
        .price(this.price)
        .sku(this.sku)
        .totalStock(this.totalStock)
        .availableStock(this.availableStock)
        .build();
  }
}
