package com.nimbusphagia.mu_libreria.model.dto.response;

import com.nimbusphagia.mu_libreria.model.entity.User;

public record UserResponse(
    String publicId,
    String name,
    String lastName,
    String address,
    String email,
    String role) {
  public static UserResponse fromEntity(User user) {
    return new UserResponse(
        user.getPublicId().toString(),
        user.getName(),
        user.getLastName(),
        user.getAddress(),
        user.getEmail(),
        user.getRole().name());
  }
}
