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

import com.nimbusphagia.mu_libreria.model.dto.product.BaseProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.EditProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaRequest;
import com.nimbusphagia.mu_libreria.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

  private final ProductService productService;

  // EDIT
  @PutMapping("/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse editProduct(
      @PathVariable UUID publicId,
      @RequestBody @Valid EditProductRequest request) {
    return productService.editProduct(publicId, request);
  }

  // Media
  @PostMapping("/{publicId}/media")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseProductResponse uploadMedia(
      @PathVariable UUID publicId,
      @RequestBody @Valid List<MediaRequest> request) {
    return productService.addMedia(publicId, request);
  }

}
