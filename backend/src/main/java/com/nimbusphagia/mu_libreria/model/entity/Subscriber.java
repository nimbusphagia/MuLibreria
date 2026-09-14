package com.nimbusphagia.mu_libreria.model.entity;

import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscriber extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private LocalDateTime subscribedAt;

  @Column(nullable = true)
  private LocalDateTime unsubscribedAt;

}
