package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.MediaProvider;
import com.nimbusphagia.mu_libreria.model.enums.MediaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_media")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductMedia extends BaseEntity {

  @Column(nullable = false)
  private String providerId;

  @Column(nullable = false)
  private String url;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MediaProvider provider;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MediaType type;

  private String altText;

  @Column(nullable = false)
  private int position;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
