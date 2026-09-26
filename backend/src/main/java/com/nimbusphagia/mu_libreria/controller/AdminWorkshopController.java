package com.nimbusphagia.mu_libreria.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.service.ProductService;
import com.nimbusphagia.mu_libreria.service.WorkshopService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/workshops")
public class AdminWorkshopController {
  private final WorkshopService workshopService;
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse create(@RequestBody @Valid WorkshopRequest request) {
    return productService.createWorkshop(request);
  }

  @PutMapping("/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public WorkshopDetailsResponse editDetails(@PathVariable UUID publicId,
      @RequestBody @Valid WorkshopDetailsRequest request) {
    return workshopService.editWorkshop(publicId, request);
  }

}
