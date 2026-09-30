package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.publisher.PublisherRequest;
import com.nimbusphagia.mu_libreria.model.dto.publisher.PublisherResponse;
import com.nimbusphagia.mu_libreria.model.entity.Publisher;
import com.nimbusphagia.mu_libreria.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
  private final PublisherRepository publisherRepository;

  public PublisherResponse create(PublisherRequest request) {
    Publisher publisher = publisherRepository.save(request.toEntity());
    return PublisherResponse.fromEntity(publisher);
  }

  @Transactional
  public PublisherResponse edit(UUID publicId, PublisherRequest request) {
    Publisher publisher = publisherRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Publisher not found."));
    publisher.updateFrom(request);
    publisherRepository.save(publisher);
    return PublisherResponse.fromEntity(publisher);
  }

  public Publisher getByPublicId(UUID publicId) {
    return publisherRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid publisher"));
  }

  public List<PublisherResponse> getAll() {
    List<Publisher> publishers = publisherRepository.findAll(Sort.by("name"));
    return publishers.stream()
        .map(PublisherResponse::fromEntity)
        .toList();
  }
}
