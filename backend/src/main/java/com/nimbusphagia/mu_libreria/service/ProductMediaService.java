package com.nimbusphagia.mu_libreria.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.model.dto.response.MediaResponse;
import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.repository.MediaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMediaService {

  private final MediaRepository mediaRepository;

  public List<MediaResponse> getByProduct(UUID productId) {
    List<ProductMedia> media = mediaRepository.findAllByProduct_PublicIdOrderByPositionAsc(productId);
    return media.stream().map(MediaResponse::fromEntity).toList();
  }
}
