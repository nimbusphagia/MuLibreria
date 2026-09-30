package com.nimbusphagia.mu_libreria.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.genre.GenreRequest;
import com.nimbusphagia.mu_libreria.model.dto.genre.GenreResponse;
import com.nimbusphagia.mu_libreria.service.GenreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
  private final GenreService genreService;

  // Admin restricted
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public GenreResponse create(@RequestBody @Valid GenreRequest request) {
    return genreService.create(request);
  }

  @PutMapping("/{genreId}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public GenreResponse edit(
      @PathVariable("genreId") UUID genreId,
      @RequestBody @Valid GenreRequest request) {
    return genreService.edit(genreId, request);
  }

  // Public
  @GetMapping
  public List<GenreResponse> getAll() {
    return genreService.getAll();
  }
}
