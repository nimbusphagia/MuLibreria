package com.nimbusphagia.mu_libreria.model.dto.request;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.nimbusphagia.mu_libreria.model.entity.ProductMedia;
import com.nimbusphagia.mu_libreria.model.enums.MediaProvider;
import com.nimbusphagia.mu_libreria.model.enums.ProductMediaType;

import jakarta.validation.constraints.NotEmpty;

public record MediaRequest(
    @NotEmpty UUID productId,
    @NotEmpty MediaProvider provider,
    @NotEmpty ProductMediaType type,
    String altText,
    int position,
    // Video only
    String url,
    String providerId,
    // Image only
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
