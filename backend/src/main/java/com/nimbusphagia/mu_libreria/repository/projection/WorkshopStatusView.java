package com.nimbusphagia.mu_libreria.repository.projection;

import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;

public interface WorkshopStatusView {
  UUID getPublicId();

  WorkshopStatus getStatus();
}
