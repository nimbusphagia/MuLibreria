package com.nimbusphagia.mu_libreria.service;

import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.repository.AuthorRepository;
import com.nimbusphagia.mu_libreria.repository.BookRepository;
import com.nimbusphagia.mu_libreria.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final PublisherRepository publisherRepository;

}
