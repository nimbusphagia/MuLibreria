package com.nimbusphagia.mu_libreria.repository.base;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
  Optional<T> findById(ID id);

  Optional<T> findByPublicId(UUID publicId);
}
