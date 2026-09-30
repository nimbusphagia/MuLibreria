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

import com.nimbusphagia.mu_libreria.model.dto.facilitator.FacilitatorRequest;
import com.nimbusphagia.mu_libreria.model.dto.facilitator.FacilitatorResponse;
import com.nimbusphagia.mu_libreria.service.FacilitatorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facilitators")
@RequiredArgsConstructor
public class FacilitatorController {
  private final FacilitatorService facilitatorService;

  // Admin restricted
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public FacilitatorResponse create(@RequestBody @Valid FacilitatorRequest request) {
    return facilitatorService.create(request);
  }

  @PutMapping("/{facilitatorId}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public FacilitatorResponse edit(
      @PathVariable("facilitatorId") UUID facilitatorId,
      @RequestBody @Valid FacilitatorRequest request) {
    return facilitatorService.edit(facilitatorId, request);
  }

  // Public
  @GetMapping
  public List<FacilitatorResponse> getAll() {
    return facilitatorService.getAll();
  }

}
