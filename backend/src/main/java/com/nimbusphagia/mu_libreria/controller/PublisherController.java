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

import com.nimbusphagia.mu_libreria.model.dto.publisher.PublisherRequest;
import com.nimbusphagia.mu_libreria.model.dto.publisher.PublisherResponse;
import com.nimbusphagia.mu_libreria.service.PublisherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {
  private final PublisherService publisherService;

  // Admin restricted
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public PublisherResponse create(@RequestBody @Valid PublisherRequest request) {
    return publisherService.create(request);
  }

  @PutMapping("/{publisherId}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public PublisherResponse edit(
      @PathVariable("publisherId") UUID publisherId,
      @RequestBody @Valid PublisherRequest request) {
    return publisherService.edit(publisherId, request);
  }

  // Public
  @GetMapping
  public List<PublisherResponse> getAll() {
    return publisherService.getAll();
  }
}
