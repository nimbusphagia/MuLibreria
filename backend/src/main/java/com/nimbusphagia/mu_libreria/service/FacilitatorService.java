package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.facilitator.FacilitatorRequest;
import com.nimbusphagia.mu_libreria.model.dto.facilitator.FacilitatorResponse;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.repository.FacilitatorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilitatorService {
  private final FacilitatorRepository facilitatorRepository;

  public FacilitatorResponse create(FacilitatorRequest request) {
    Facilitator facilitator = facilitatorRepository.save(request.toEntity());
    return FacilitatorResponse.fromEntity(facilitator);
  }

  @Transactional
  public FacilitatorResponse edit(UUID publicId, FacilitatorRequest request) {
    Facilitator facilitator = facilitatorRepository.findByPublicId(publicId)
        .orElseThrow(() -> new BadRequestException("Facilitator not found."));
    facilitator.updateFrom(request);
    facilitatorRepository.save(facilitator);
    return FacilitatorResponse.fromEntity(facilitator);
  }

  public List<FacilitatorResponse> getAll() {
    List<Facilitator> facilitators = facilitatorRepository.findAll(Sort.by("lastName", "name"));
    return facilitators.stream()
        .map(FacilitatorResponse::fromEntity)
        .toList();
  }

  public Facilitator getByPublicId(UUID facilitatorId) {
    return facilitatorRepository.findByPublicId(facilitatorId)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid facilitator."));

  }
}
