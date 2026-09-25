package com.nimbusphagia.mu_libreria.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.enums.ProductType;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, Long> {
  Page<Product> findAllByType(ProductType type, Pageable pageable);

  Boolean existsByPublicId(UUID productId);
}
