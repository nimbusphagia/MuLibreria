package com.nimbusphagia.mu_libreria.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, Long> {
  Page<Product> findAllByType(ProductType type, Pageable pageable);

  Boolean existsByPublicId(UUID productId);

  @Modifying
  @Transactional
  @Query("UPDATE Product p SET p.deletedAt = null WHERE p.public_id = :publicId")
  int restoreByPublicId(@Param("publicId") UUID publicId);
}
