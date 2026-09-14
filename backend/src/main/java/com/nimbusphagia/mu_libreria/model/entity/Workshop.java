package com.nimbusphagia.mu_libreria.model.entity;

import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Workshop extends BaseEntity {

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facilitator_id")
  private Facilitator facilitator;

  @ElementCollection
  @CollectionTable(name = "workshop_images", joinColumns = @JoinColumn(name = "workshop_id"))
  @Column(name = "image_url")
  private List<String> images;

  @Column(nullable = false)
  private LocalDateTime datetime;

  @Column(nullable = false)
  private Duration duration;

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  private Integer capacity;

  @Column(nullable = false)
  private Integer registeredCount;

  @Enumerated(EnumType.STRING)
  private WorkshopStatus status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;

}
