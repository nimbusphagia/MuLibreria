package com.nimbusphagia.mu_libreria.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.PaymentMethod;
import com.nimbusphagia.mu_libreria.model.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentMethod paymentMethod;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus paymentStatus;

  @Column(nullable = true)
  private LocalDateTime confirmedAt;

  @Column(nullable = true)
  private String providerReference;
}
