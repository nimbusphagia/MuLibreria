package com.nimbusphagia.mu_libreria.model.entity;

import java.util.List;

import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workshops")
public class Workshop extends BaseEntity {

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facilitator_id", nullable = false)
  private Facilitator facilitator;

  @ElementCollection
  @CollectionTable(name = "workshop_images", joinColumns = @JoinColumn(name = "workshop_id"))
  @Column(name = "image_url")
  private List<String> images;

  @Column(nullable = false)
  private LocalDateTime datetime;

  @Column(nullable = false)
  private Integer durationMinutes;

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  private Integer capacity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WorkshopStatus status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;

  public void updateFrom(WorkshopDetailsRequest request, Facilitator facilitator) {
    this.title = request.title();
    this.description = request.description();
    this.facilitator = facilitator;
    this.images = request.images();
    this.datetime = request.datetime();
    this.durationMinutes = request.durationMinutes();
    this.location = request.location();
    this.capacity = request.capacity();
    this.status = request.status();
  }
}
