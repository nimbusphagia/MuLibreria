package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.model.dto.request.MediaRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.CloudinaryResponse;
import com.nimbusphagia.mu_libreria.model.dto.response.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.Product;
import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.model.enums.MediaProvider;
import com.nimbusphagia.mu_libreria.model.enums.ProductMediaType;
import com.nimbusphagia.mu_libreria.repository.MediaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMediaService {

  private final MediaRepository mediaRepository;
  private final CloudinaryService cloudinaryService;

  // GET
  public List<MediaResponse> getByProduct(UUID productId) {
    List<ProductMedia> media = mediaRepository.findAllByProduct_PublicIdOrderByPositionAsc(productId);
    return media.stream().map(MediaResponse::fromEntity).toList();
  }

  public Map<UUID, List<MediaResponse>> getByProducts(List<UUID> productIds) {
    return mediaRepository.findAllByProduct_PublicIdIn(productIds).stream()
        .collect(Collectors.groupingBy(
            m -> m.getProduct().getPublicId(),
            Collectors.mapping(MediaResponse::fromEntity, Collectors.toList())));
  }

  // Upload to product
  @Transactional
  public MediaResponse createAndAttachToProduct(MediaRequest request, Product product) {
    guardMedia(request);
    ProductMedia productMedia = null;
    if (request.type() == ProductMediaType.IMAGE) {
      CloudinaryResponse upload = cloudinaryService.upload(request.file());
      productMedia = request.toEntity(upload.url(), upload.publicId());
    } else if (request.type() == ProductMediaType.VIDEO) {
      productMedia = request.toEntity(null, null);
    }
    productMedia.setProduct(product);
    productMedia = mediaRepository.save(productMedia);
    return MediaResponse.fromEntity(productMedia);
  }

  private void guardMedia(MediaRequest request) {
    switch (request.type()) {
      case VIDEO:
        if (request.url() == null || request.url().isBlank()) {
          throw new BadRequestException("Video uploads have to include a url.");
        }
        if (request.provider() != MediaProvider.YOUTUBE) {
          throw new BadRequestException("Only youtube videos are allowed.");
        }
        break;
      case IMAGE:
        if (request.provider() != MediaProvider.CLOUDINARY) {
          throw new BadRequestException("Only cloudinary image uploads are supported.");
        }
        break;
      default:
        break;
    }
  }

}
