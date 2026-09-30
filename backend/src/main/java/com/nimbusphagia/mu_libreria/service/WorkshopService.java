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
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsRequest;
import com.nimbusphagia.mu_libreria.model.dto.product.workshop.WorkshopDetailsResponse;
import com.nimbusphagia.mu_libreria.model.entity.Facilitator;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.Workshop;
import com.nimbusphagia.mu_libreria.model.enums.WorkshopStatus;
import com.nimbusphagia.mu_libreria.repository.WorkshopRepository;
import com.nimbusphagia.mu_libreria.repository.projection.WorkshopStatusView;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkshopService {
  private final WorkshopRepository workshopRepository;
  private final FacilitatorService facilitatorService;

  // GET
  public WorkshopDetailsResponse getByProduct(UUID productId) {
    Workshop workshop = workshopRepository.findByProduct_PublicId(productId);
    return WorkshopDetailsResponse.fromEntity(workshop);
  }

  public Map<UUID, WorkshopDetailsResponse> getByProducts(List<UUID> productIds) {
    return workshopRepository
        .findAllByProduct_PublicIdIn(productIds)
        .stream()
        .collect(Collectors.toMap(
            w -> w.getProduct().getPublicId(),
            w -> WorkshopDetailsResponse.fromEntity(w)));
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
  @Transactional
  public WorkshopDetailsResponse createAndAttach(Product product, WorkshopDetailsRequest details) {
    if (details == null) {
      throw new BadRequestException("Workshop details are required.");
    }
    Facilitator facilitator = facilitatorService.getFacilitator(details.facilitatorId());
    Workshop workshop = details.toEntity(product, facilitator);
    workshopRepository.save(workshop);
    return WorkshopDetailsResponse.fromEntity(workshop);
  }

  public Map<UUID, WorkshopStatus> getStatusByProducts(List<UUID> ids) {
    return workshopRepository.findStatusesByProduct_PublicIds(ids).stream()
        .collect(Collectors.toMap(WorkshopStatusView::getPublicId, WorkshopStatusView::getStatus));
  }

  public WorkshopStatus getStatusByProduct(UUID productId) {
    return workshopRepository.findStatusByProduct_PublicId(productId)
        .orElseThrow(() -> new BadRequestException("Workshop not found for product" + productId));
  }

  public Boolean isAvailable(UUID productId) {
    return workshopRepository.existsByStatusAndProduct_PublicId(productId, WorkshopStatus.REGISTRATION_OPEN);
  }
}
