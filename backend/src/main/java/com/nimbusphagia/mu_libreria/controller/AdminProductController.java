package com.nimbusphagia.mu_libreria.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.request.BookRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.MediaRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

  private final ProductService productService;

  // EDIT
  @PutMapping("/book/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductSummaryResponse editBookProduct(
      @PathVariable UUID publicId,
      @RequestBody @Valid BookRequest request) {
    return productService.editProductDetails(publicId, request, null);
  }

  @PutMapping("/workshop/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductSummaryResponse editWorkshopProduct(
      @PathVariable UUID publicId,
      @RequestBody @Valid WorkshopRequest request) {
    return productService.editProductDetails(publicId, null, request);
  }

  // Media
  @PostMapping("/{publicId}/media")
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse uploadMedia(@RequestBody @Valid List<MediaRequest> request) {

  }

}
