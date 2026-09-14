package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @Column(unique = true, nullable = false)
  private User user;

  @Column(nullable = false)
  private Double totalAmount;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
