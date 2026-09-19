package com.nimbusphagia.mu_libreria.service;

import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
  private final PublisherRepository publisherRepository;
}
