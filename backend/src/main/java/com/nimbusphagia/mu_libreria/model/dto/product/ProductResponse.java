package com.nimbusphagia.mu_libreria.model.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "productType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BookProductResponse.class, name = "BOOK"),
    @JsonSubTypes.Type(value = WorkshopProductResponse.class, name = "WORKSHOP")
})
public sealed interface ProductResponse permits BookProductResponse, WorkshopProductResponse {
  UUID publicId();

  String name();

  String description();

  BigDecimal price();

  List<MediaResponse> media();

  String sku();

  ProductType productType();

  Availability availability();

  Instant createdAt();
}
