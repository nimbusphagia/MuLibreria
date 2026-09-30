package com.nimbusphagia.mu_libreria.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.genre.GenreRequest;
import com.nimbusphagia.mu_libreria.model.dto.genre.GenreResponse;
import com.nimbusphagia.mu_libreria.model.entity.Genre;
import com.nimbusphagia.mu_libreria.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
  private final GenreRepository genreRepository;

  public GenreResponse create(GenreRequest request) {
    Genre entity = genreRepository.save(request.toEntity());
    return GenreResponse.fromEntity(entity);
  }

  @Transactional
  public GenreResponse edit(UUID publicId, GenreRequest request) {
    Genre genre = genreRepository.findByPublicId(publicId)
        .orElseThrow(() -> new BadRequestException("Genre not found."));
    genre.updateFrom(request);
    genreRepository.save(genre);
    return GenreResponse.fromEntity(genre);
  }

  public List<GenreResponse> getAll() {
    List<Genre> genres = genreRepository.findAll(Sort.by("lastName", "name"));
    return genres.stream()
        .map(GenreResponse::fromEntity)
        .toList();
  }

  // Book utilities
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
