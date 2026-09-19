package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.WorkshopResponse;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.FacilitatorRepository;
import com.nimbusphagia.mu_libreria.repository.WorkshopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkshopService {
  private final WorkshopRepository workshopRepository;
  private final FacilitatorRepository facilitatorRepository;

  @Transactional
  public WorkshopResponse editWorkshop(UUID publicId, WorkshopDetailsRequest details) {

    Workshop existingWorkshop = workshopRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Workshop not found."));

    Facilitator facilitator = facilitatorRepository.findByPublicId(details.facilitatorId())
        .orElseThrow(() -> new ResourceNotFoundException("Invalid facilitator."));

    existingWorkshop.updateFrom(details, facilitator);
    return WorkshopResponse.fromEntity(workshopRepository.save(existingWorkshop));
  }

  public List<WorkshopResponse> getWorkshops(WorkshopStatus status) {
    Sort sort = Sort.by("createdAt").descending();
    List<Workshop> entities = (status == null)
        ? workshopRepository.findAll(sort)
        : workshopRepository.findByStatus(status);
    return entities.stream()
        .map(WorkshopResponse::fromEntity)
        .toList();
  }

}
