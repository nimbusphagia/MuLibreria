package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.AuthorRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.AuthorResponse;
import com.nimbusphagia.mu_libreria.model.entity.Author;
import com.nimbusphagia.mu_libreria.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;

  public AuthorResponse create(AuthorRequest request) {
    Author entity = authorRepository.save(request.toEntity());
    return AuthorResponse.fromEntity(entity);
  }

  public AuthorResponse edit(UUID publicId, AuthorRequest request) {
    Author existingAuthor = authorRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Author not found."));
    existingAuthor.updateFrom(request);
    Author entity = authorRepository.save(existingAuthor);
    return AuthorResponse.fromEntity(entity);
  }

  public List<Author> getAll() {
    return authorRepository.findAll(Sort.by("lastName", "name"));
  }
}
