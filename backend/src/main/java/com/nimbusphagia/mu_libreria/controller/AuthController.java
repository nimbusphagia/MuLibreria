package com.nimbusphagia.mu_libreria.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.response.LoginResponse;
import com.nimbusphagia.mu_libreria.model.dto.request.LoginRequest;
import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Valid LoginRequest request) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    User user = (User) auth.getPrincipal();
    String token = jwtService.generateToken(user);

    return LoginResponse.of(token);
  }
}
