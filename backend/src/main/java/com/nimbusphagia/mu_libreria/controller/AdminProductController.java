package com.nimbusphagia.mu_libreria.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.request.ProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductSummaryResponse;
import com.nimbusphagia.mu_libreria.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

  private final ProductService productService;

  @PutMapping("/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductSummaryResponse editProduct(
      @PathVariable UUID publicId,
      @RequestBody @Valid ProductRequest request) {
    return productService.editProduct(publicId, request);
  }

  @DeleteMapping("/{publicId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void softDelete(@PathVariable UUID publicId) {
    productService.softDelete(publicId);
  }
}
