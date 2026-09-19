package com.nimbusphagia.mu_libreria.repository;

import java.util.Optional;

import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {
  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);
}
