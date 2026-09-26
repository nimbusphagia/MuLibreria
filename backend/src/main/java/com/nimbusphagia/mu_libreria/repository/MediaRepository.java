package com.nimbusphagia.mu_libreria.repository;

import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface MediaRepository extends BaseRepository<ProductMedia, Long> {
  List<ProductMedia> findAllByProduct_PublicIdOrderByPositionAsc(UUID productId);

  List<ProductMedia> findAllByProduct_PublicIdIn(List<UUID> productIds);
}
