package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.author.AuthorRequest;
import com.nimbusphagia.mu_libreria.model.dto.author.AuthorResponse;
import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;

  public AuthorResponse create(AuthorRequest request) {
    Author author = authorRepository.save(request.toEntity());
    return AuthorResponse.fromEntity(author);
  }

  @Transactional
  public AuthorResponse edit(UUID publicId, AuthorRequest request) {
    Author author = authorRepository.findByPublicId(publicId)
        .orElseThrow(() -> new BadRequestException("Author not found."));
    author.updateFrom(request);
    authorRepository.save(author);
    return AuthorResponse.fromEntity(author);
  }

  public Author getByPublicId(UUID publicId) {
    return authorRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid author"));
  }

  public List<AuthorResponse> getAll() {
    List<Author> authors = authorRepository.findAll(Sort.by("lastName", "name"));
    return authors.stream()
        .map(AuthorResponse::fromEntity)
        .toList();
  }
}
