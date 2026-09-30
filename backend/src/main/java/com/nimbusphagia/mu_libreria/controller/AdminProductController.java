package com.nimbusphagia.mu_libreria.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.product.BaseProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.BookProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.CreateBookRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.CreateWorkshopRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.EditProductRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.ProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.WorkshopProductResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.book.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.book.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.product.media.MediaRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.service.BookService;
import com.nimbusphagia.mu_libreria.service.ProductService;
import com.nimbusphagia.mu_libreria.service.WorkshopService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminProductController {

  private final ProductService productService;
  private final WorkshopService workshopService;
  private final BookService bookService;

  // Create book
  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  public BookProductResponse createBook(@RequestBody @Valid CreateBookRequest request) {
    return productService.createBook(request);
  }

  // Edit book details
  @PutMapping("/book/{productId}/details")
  @ResponseStatus(HttpStatus.OK)
  public BookDetailsResponse editDetails(@PathVariable UUID productId, @RequestBody @Valid BookDetailsRequest request) {
    return bookService.editDetails(productId, request);
  }

  // Delete book
  @DeleteMapping("/books/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable UUID productId) {
    productService.deleteBook(productId);
  }

  // Create Workshop
  @PostMapping("/workshops")
  @ResponseStatus(HttpStatus.CREATED)
  public WorkshopProductResponse create(@RequestBody @Valid CreateWorkshopRequest request) {
    return productService.createWorkshop(request);
  }

  // Edit Workshop details
  @PutMapping("workshops/{publicId}/details")
  @ResponseStatus(HttpStatus.OK)
  public WorkshopDetailsResponse editDetails(@PathVariable UUID publicId,
      @RequestBody @Valid WorkshopDetailsRequest request) {
    return workshopService.editDetails(publicId, request);
  }

  // Delete Workshop
  @DeleteMapping("/workshops/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteWorkshop(@PathVariable UUID productId) {
    productService.deleteWorkshop(productId);
  }

  // Upload media to product
  @PostMapping("products/{publicId}/media")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseProductResponse uploadMedia(
      @PathVariable UUID publicId,
      @RequestBody @Valid List<MediaRequest> request) {
    return productService.addMedia(publicId, request);
  }

  // Edit base product
  @PutMapping("/products/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse editProduct(
      @PathVariable UUID publicId,
      @RequestBody @Valid EditProductRequest request) {
    return productService.editProduct(publicId, request);
  }

}
