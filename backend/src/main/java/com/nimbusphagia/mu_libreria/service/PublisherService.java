package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public PublisherResponse createPublisher(PublisherRequest request) {
    Publisher entity = publisherRepository.save(request.toEntity());
    return PublisherResponse.fromEntity(entity);
  }

  @Transactional
  public PublisherResponse editPublisher(UUID publicId, PublisherRequest request) {
    Publisher existingPublisher = publisherRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Publisher not found."));
    existingPublisher.updateFrom(request);
    publisherRepository.save(existingPublisher);
    return PublisherResponse.fromEntity(existingPublisher);
  }

  public List<PublisherResponse> getPublishers() {
    List<Publisher> entities = publisherRepository.findAll(Sort.by("name"));
    return entities.stream()
        .map(PublisherResponse::fromEntity)
        .toList();
  }
}
