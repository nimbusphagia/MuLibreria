package com.nimbusphagia.mu_libreria.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;
import com.nimbusphagia.mu_libreria.repository.projection.WorkshopStatusView;

public interface WorkshopRepository extends BaseRepository<Workshop, Long> {
  Workshop findByProduct_PublicId(UUID publicId);

  List<Workshop> findByStatus(WorkshopStatus status);

  List<Workshop> findAllByProduct_PublicIdIn(List<UUID> productIds);

  Boolean existsByStatusAndProduct_PublicId(UUID productId, WorkshopStatus status);

  @Query("""
      select w.product.publicId as publicId, w.status as status
      from Workshop w
      where w.product.publicId in :ids
      """)
  List<WorkshopStatusView> findStatusesByProduct_PublicIds(@Param("ids") Collection<UUID> ids);

  @Query("""
      select w.status from Workshop w
      where w.product.publicId = :publicId
      """)
  Optional<WorkshopStatus> findStatusByProduct_PublicId(@Param("publicId") UUID publicId);

}
