package com.nimbusphagia.mu_libreria.mapper;

import java.util.Objects;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.Availability;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

public final class ProductAvailabilityMapper {

  private static final int LOW_STOCK_THRESHOLD = 3;

  private ProductAvailabilityMapper() {
  }

  public static Availability fromStatus(WorkshopStatus status) {
    return switch (status) {
      case REGISTRATION_OPEN -> Availability.OPEN;
      case IN_PROGRESS, COMPLETED, CANCELLED -> Availability.CLOSED;
    };
  }

  public static Availability fromBooleanStatus(Boolean isAvailable) {
    return isAvailable ? Availability.OPEN : Availability.CLOSED;
  }

  public static Availability fromStock(int available) {
    if (available <= 0)
      return Availability.SOLD_OUT;
    if (available <= LOW_STOCK_THRESHOLD)
      return Availability.LOW_STOCK;
    return Availability.IN_STOCK;
  }

  public static Availability fromProduct(Product p, WorkshopStatus workshopStatus) {
    return switch (p.getType()) {
      case WORKSHOP ->
        fromStatus(Objects.requireNonNull(workshopStatus, "Workshop status required"));
      case BOOK, MERCH -> fromStock(p.getAvailableStock());
    };
  }
}
