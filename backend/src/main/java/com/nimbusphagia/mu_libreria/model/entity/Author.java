package com.nimbusphagia.mu_libreria.model.entity;

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
public class Author extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String lastname;

}
