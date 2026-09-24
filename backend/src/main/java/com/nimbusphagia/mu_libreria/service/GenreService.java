package com.nimbusphagia.mu_libreria.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
  private final GenreRepository genreRepository;

  public Set<Genre> resolveGenres(Set<UUID> genreIds) {
    if (genreIds == null || genreIds.isEmpty()) {
      return new HashSet<>();
    }
    List<Genre> found = genreRepository.findAllByPublicIdIn(genreIds);
    if (found.size() != genreIds.size()) {
      throw new ResourceNotFoundException("One or more genres not found.");
    }
    return new HashSet<>(found);
  }

}
