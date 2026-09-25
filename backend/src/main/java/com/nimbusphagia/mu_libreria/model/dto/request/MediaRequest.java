package com.nimbusphagia.mu_libreria.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.model.enums.MediaProvider;
import com.nimbusphagia.mu_libreria.model.enums.ProductMediaType;

import jakarta.validation.constraints.NotNull;

public record MediaRequest(
    @NotNull MediaProvider provider,
    @NotNull ProductMediaType type,
    String altText,
    int position,
    String url,
    String providerId,
    MultipartFile file) {
  public ProductMedia toEntity(String url, String providerId) {
    return ProductMedia.builder()
        .type(this.type)
        .altText(this.altText)
        .position(this.position)
        .provider(this.provider)
        .url(url != null ? url : this.url)
        .providerId(providerId != null ? providerId : this.providerId)
        .build();
  }
}
