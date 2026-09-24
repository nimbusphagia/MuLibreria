package com.nimbusphagia.mu_libreria.repository;

import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface WorkshopRepository extends BaseRepository<Workshop, Long> {
  Workshop findByProduct_PublicId(UUID publicId);

  List<Workshop> findByStatus(WorkshopStatus status);

  Boolean existsByPublicIdAndStatus(UUID publicId, WorkshopStatus status);
}
