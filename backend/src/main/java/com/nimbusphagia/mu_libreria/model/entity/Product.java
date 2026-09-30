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

import com.nimbusphagia.mu_libreria.model.dto.product.EditProductRequest;
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

  @Column(unique = true)
  private String sku;

  @Column(nullable = true)
  private Integer totalStock;

  @Column(nullable = true)
  private Integer availableStock;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductType type;

  public void updateFrom(EditProductRequest p) {
    this.name = p.name();
    this.description = p.description();
    this.price = p.price();
    this.sku = p.sku();
    if (p.type() != ProductType.WORKSHOP) {
      this.totalStock = p.totalStock();
      this.availableStock = p.availableStock();
    }
  }
}
