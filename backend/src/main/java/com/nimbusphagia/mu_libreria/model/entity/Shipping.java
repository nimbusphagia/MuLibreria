package com.nimbusphagia.mu_libreria.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.ShippingMethod;
import com.nimbusphagia.mu_libreria.model.enums.ShippingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Shipping extends BaseEntity {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false, unique = true)
  private Order order;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ShippingMethod method;

  private String trackingNumber;

  @Column(nullable = false)
  private BigDecimal cost;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String recipient;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ShippingStatus status;

  private LocalDateTime shippedAt;

  private LocalDateTime deliveredAt;

}
