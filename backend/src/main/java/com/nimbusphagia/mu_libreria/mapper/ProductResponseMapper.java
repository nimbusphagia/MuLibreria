package com.nimbusphagia.mu_libreria.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nimbusphagia.mu_libreria.model.dto.product.BookProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.WorkshopProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

@Component
public class ProductResponseMapper {

  public ProductResponse toResponse(Product p, WorkshopStatus workshopStatus, List<MediaResponse> media) {
    return switch (p.getType()) {
      case BOOK -> BookProductResponse.fromEntity(p, media);
      case WORKSHOP -> WorkshopProductResponse.fromEntity(p, workshopStatus, media);
      default -> throw new IllegalStateException("Unsupported product type: " + p.getType());
    };
  }
}
