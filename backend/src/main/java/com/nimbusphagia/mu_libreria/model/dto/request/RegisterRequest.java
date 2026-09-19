package com.nimbusphagia.mu_libreria.model.dto.request;

import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.model.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Name is empty") String name,
    @NotBlank(message = "Last Name is empty") String lastName,
    String address,
    @NotBlank @Email(message = "Email isn't valid") String email,
    @NotBlank(message = "Password is required") @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$", message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character") String password) {

  public User toEntity(String hashedPassword, UserRole role) {
    return User.builder()
        .email(this.email)
        .name(this.name)
        .lastName(this.lastName)
        .address(this.address)
        .passwordHash(hashedPassword)
        .role(role)
        .build();
  }
}
