package com.nimbusphagia.mu_libreria.service;

import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;

}
