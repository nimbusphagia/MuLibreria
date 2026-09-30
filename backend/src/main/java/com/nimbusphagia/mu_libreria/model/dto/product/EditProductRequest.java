package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditProductRequest(
    @NotBlank String name,
    String description,
    @NotNull BigDecimal price,
    String sku,
    @NotNull ProductType type,
    Integer totalStock,
    Integer availableStock,
    WorkshopStatus status) {
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
