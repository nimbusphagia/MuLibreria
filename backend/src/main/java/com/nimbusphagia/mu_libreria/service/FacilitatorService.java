package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.FacilitatorRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.FacilitatorResponse;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.repository.FacilitatorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilitatorService {
  private final FacilitatorRepository facilitatorRepository;

  public FacilitatorResponse createFacilitator(FacilitatorRequest request) {
    Facilitator entity = facilitatorRepository.save(request.toEntity());
    return FacilitatorResponse.fromEntity(entity);
  }

  @Transactional
  public FacilitatorResponse editFacilitator(UUID publicId, FacilitatorRequest request) {
    Facilitator existingFacilitator = facilitatorRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Facilitator not found."));
    existingFacilitator.updateFrom(request);
    facilitatorRepository.save(existingFacilitator);
    return FacilitatorResponse.fromEntity(existingFacilitator);
  }

  public List<FacilitatorResponse> getFacilitators() {
    List<Facilitator> entities = facilitatorRepository.findAll(Sort.by("lastName", "name"));
    return entities.stream()
        .map(FacilitatorResponse::fromEntity)
        .toList();
  }

  public Facilitator getFacilitator(UUID facilitatorId) {
    return facilitatorRepository.findByPublicId(facilitatorId)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid facilitator."));

  }
}
