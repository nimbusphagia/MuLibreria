package com.nimbusphagia.mu_libreria.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank @Email(message = "Email isn't valid") String email,
    @NotBlank(message = "Password is required") String password) {

}
