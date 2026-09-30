package com.nimbusphagia.mu_libreria.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.page.PageResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.BaseProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.BookProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.WorkshopProductResponse;
import com.nimbusphagia.mu_libreria.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/workshops")
  public PageResponse<BaseProductResponse> getWorkshops(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {
    return productService.getAllWorkshops(page, size, sortBy);
  }

  @GetMapping("/books")
  public PageResponse<BaseProductResponse> getBooks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {
    return productService.getAllBooks(page, size, sortBy);
  }

  @GetMapping("/{productId}/workshops")
  public WorkshopProductResponse getWorkshop(@PathVariable("productId") UUID productId) {
    return productService.getWorkshop(productId);
  }

  @GetMapping("/{productId}/books")
  public BookProductResponse getBook(@PathVariable("productId") UUID productId) {
    return productService.getBook(productId);
  }

}
