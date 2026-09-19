package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.PublisherRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.PublisherResponse;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;
import com.nimbusphagia.mu_libreria.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
  private final PublisherRepository publisherRepository;

  public PublisherResponse create(PublisherRequest request) {
    Publisher entity = publisherRepository.save(request.toEntity());
    return PublisherResponse.fromEntity(entity);
  }

  public PublisherResponse edit(UUID publicId, PublisherRequest request) {
    Publisher existingPublisher = publisherRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Publisher not found."));
    existingPublisher.updateFrom(request);
    Publisher entity = publisherRepository.save(existingPublisher);
    return PublisherResponse.fromEntity(entity);
  }

  public List<Publisher> getAll() {
    return publisherRepository.findAll(Sort.by("name"));
  }
}
