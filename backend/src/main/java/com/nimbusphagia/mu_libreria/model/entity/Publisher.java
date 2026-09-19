package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.dto.request.PublisherRequest;
import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "publishers")
public class Publisher extends BaseEntity {
  @Column(nullable = false)
  private String name;

  public void updateFrom(PublisherRequest request) {
    this.name = request.name();
  }
}
