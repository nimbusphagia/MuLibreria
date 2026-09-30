package com.nimbusphagia.mu_libreria.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nimbusphagia.mu_libreria.model.dto.product.BookProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.WorkshopProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.book.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;

@Component
public class ProductResponseMapper {

  public ProductResponse toResponse(Product p,
      BookDetailsResponse b,
      WorkshopDetailsResponse w,
      List<MediaResponse> m) {
    return switch (p.getType()) {
      case BOOK -> BookProductResponse.fromEntity(p, b, m);
      case WORKSHOP -> WorkshopProductResponse.fromEntity(p, w, m);
      default -> throw new IllegalStateException("Unsupported product type: " + p.getType());
    };
  }
}
