package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.exception.ResourceNotFoundException;
import com.nimbusphagia.mu_libreria.model.dto.request.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.WorkshopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkshopService {
  private final WorkshopRepository workshopRepository;
  private final FacilitatorService facilitatorService;

  // GET
  public Workshop getByProduct(UUID productId) {
    return workshopRepository.findByProduct_PublicId(productId);
  }

  public Map<UUID, Workshop> getByProducts(List<UUID> productIds) {
    return workshopRepository.findAllByProduct_PublicIdIn(productIds).stream()
        .collect(Collectors.toMap(b -> b.getProduct().getPublicId(), b -> b));
  }

  public List<WorkshopDetailsResponse> getWorkshops(WorkshopStatus status) {
    Sort sort = Sort.by("createdAt").descending();
    List<Workshop> entities = (status == null)
        ? workshopRepository.findAll(sort)
        : workshopRepository.findByStatus(status);
    return entities.stream()
        .map(WorkshopDetailsResponse::fromEntity)
        .toList();
  }

  // EDIT
  @Transactional
  public WorkshopDetailsResponse editWorkshop(UUID publicId, WorkshopDetailsRequest details) {
    Workshop existingWorkshop = workshopRepository.findByPublicId(publicId)
        .orElseThrow(() -> new ResourceNotFoundException("Workshop not found."));
    Facilitator facilitator = facilitatorService.getFacilitator(details.facilitatorId());
    existingWorkshop.updateFrom(details, facilitator);
    return WorkshopDetailsResponse.fromEntity(workshopRepository.save(existingWorkshop));
  }

  // For product creation
  public Workshop createAndAttach(Product product, WorkshopDetailsRequest details) {
    if (details == null) {
      throw new BadRequestException("Workshop details are required.");
    }
    Facilitator facilitator = facilitatorService.getFacilitator(details.facilitatorId());
    Workshop workshop = details.toEntity(product, facilitator);
    return workshopRepository.save(workshop);
  }

  public Boolean isAvailable(UUID productId) {
    return workshopRepository.existsByStatusAndProduct_PublicId(productId, WorkshopStatus.REGISTRATION_OPEN);
  }
}
