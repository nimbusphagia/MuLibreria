package com.nimbusphagia.mu_libreria.model.base;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeletableEntity extends BaseEntity {

  private Instant deletedAt;

  public boolean isDeleted() {
    return deletedAt != null;
  }
}
