package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.dto.request.FacilitatorRequest;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "facilitators")
public class Facilitator extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String lastName;

  public void updateFrom(FacilitatorRequest request) {
    this.name = request.name();
    this.lastName = request.lastName();
  }
}
