package com.nimbusphagia.mu_libreria.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nimbusphagia.mu_libreria.exception.BadRequestException;
import com.nimbusphagia.mu_libreria.model.dto.response.CloudinaryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

  private final Cloudinary cloudinary;

  public CloudinaryResponse upload(MultipartFile file) {
    Map<?, ?> result;
    try {
      result = cloudinary.uploader().upload(
          file.getBytes(),
          ObjectUtils.asMap("folder", "mu_products"));
    } catch (IOException e) {
      throw new BadRequestException("Failed to upload to cloudinary: " + e.getMessage());
    }

    String publicId = (String) result.get("public_id");
    String url = (String) result.get("secure_url");

    if (publicId == null || publicId.isBlank() || url == null || url.isBlank()) {
      throw new IllegalStateException(
          "Cloudinary upload response missing public_id or secure_url: " + result);
    }

    return new CloudinaryResponse(publicId, url);
  }

  public void delete(String publicId) {
    try {
      cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new BadRequestException("Failed to delete from cloudinary: " + e.getMessage());
    }
  }
}
