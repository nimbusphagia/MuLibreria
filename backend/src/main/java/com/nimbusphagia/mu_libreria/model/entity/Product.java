package com.nimbusphagia.mu_libreria.model.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.nimbusphagia.mu_libreria.model.dto.request.ProductRequest;
import com.nimbusphagia.mu_libreria.model.entity.base.SoftDeletableEntity;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SQLRestriction("deleted_at IS NULL")
@Table(name = "products")
public class Product extends SoftDeletableEntity {

  @Column(nullable = false)
  private String name;

  @Column(length = 2000)
  private String description;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private String imageUrl;

  @Column(unique = true)
  private String sku;

  // totalStock/availableStock apply only to BOOK and MERCH products.
  @Column(nullable = true)
  private Integer totalStock;

  @Column(nullable = true)
  private Integer availableStock;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductType type;

  public void updateFrom(ProductRequest request) {
    this.name = request.name();
    this.description = request.description();
    this.price = request.price();
    this.imageUrl = request.imageUrl();
    this.sku = request.sku();
    this.totalStock = request.totalStock();
    this.availableStock = request.availableStock();
  }
}
