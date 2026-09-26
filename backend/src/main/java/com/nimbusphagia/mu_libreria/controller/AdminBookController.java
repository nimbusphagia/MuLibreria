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

import com.nimbusphagia.mu_libreria.model.dto.request.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.BookRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.BookDetailsResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.ProductResponse;
import com.nimbusphagia.mu_libreria.service.BookService;
import com.nimbusphagia.mu_libreria.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/books")
public class AdminBookController {
  private final ProductService productService;
  private final BookService bookService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createBook(@RequestBody @Valid BookRequest request) {
    return productService.createBook(request);
  }

  @PutMapping("/{publicId}")
  @ResponseStatus(HttpStatus.OK)
  public BookDetailsResponse editDetails(@PathVariable UUID publicId, @RequestBody @Valid BookDetailsRequest request) {
    return bookService.editBook(publicId, request);
  }
}
