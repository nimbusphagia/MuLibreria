package com.nimbusphagia.mu_libreria.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface GenreRepository extends BaseRepository<Genre, Long> {
  List<Genre> findAllByPublicIdIn(Set<UUID> publicIds);
}
