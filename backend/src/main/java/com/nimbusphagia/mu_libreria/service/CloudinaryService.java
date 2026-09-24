package com.nimbusphagia.mu_libreria.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nimbusphagia.mu_libreria.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

  private final Cloudinary cloudinary;

  public String upload(MultipartFile file) {
    try {
      Map<?, ?> result = cloudinary.uploader().upload(
          file.getBytes(),
          ObjectUtils.asMap("folder", "mu_products"));
      return (String) result.get("secure_url");
    } catch (IOException e) {
      throw new BadRequestException("Failed to upload to cloudinary: " + e.getMessage());
    }
  }

  public void delete(String publicId) {
    try {
      cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new BadRequestException("Failed to delete from cloudinary: " + e.getMessage());
    }
  }
}
