package com.nimbusphagia.mu_libreria.model.dto.response;

import java.time.Instant;

import com.nimbusphagia.mu_libreria.model.entity.User;

public record UserResponse(
    String publicId,
    String name,
    String lastName,
    String address,
    String email,
    String role,
    Instant createdAt) {
  public static UserResponse fromEntity(User user) {
    return new UserResponse(
        user.getPublicId().toString(),
        user.getName(),
        user.getLastName(),
        user.getAddress(),
        user.getEmail(),
        user.getRole().name(),
        user.getCreatedAt());
  }
}
