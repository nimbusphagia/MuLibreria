package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;

import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWorkshopRequest(
    @NotBlank String name,
    String description,
    @NotNull BigDecimal price,
    String sku,
    WorkshopDetailsRequest details) {
  public Product toEntity() {
    return Product.builder()
        .name(this.name)
        .description(this.description)
        .price(this.price)
        .sku(this.sku)
        .build();
  }
}
