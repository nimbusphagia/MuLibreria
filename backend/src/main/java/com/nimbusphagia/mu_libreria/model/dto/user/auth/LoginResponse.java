package com.nimbusphagia.mu_libreria.model.dto.user.auth;

public record LoginResponse(
    String token,
    String tokenType) {
  public static LoginResponse of(String token) {
    return new LoginResponse(token, "Bearer");
  }
}
