package com.nimbusphagia.mu_libreria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusphagia.mu_libreria.model.dto.response.LoginResponse;
import com.nimbusphagia.mu_libreria.model.dto.request.LoginRequest;
import com.nimbusphagia.mu_libreria.model.dto.request.RegisterRequest;
import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.model.enums.UserRole;
import com.nimbusphagia.mu_libreria.security.JwtService;
import com.nimbusphagia.mu_libreria.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody @Valid LoginRequest request) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    User user = (User) auth.getPrincipal();
    String token = jwtService.generateToken(user);

    return LoginResponse.of(token);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResponse register(@RequestBody @Valid RegisterRequest request) {
    User user = userService.registerUser(request, UserRole.CLIENT);
    String token = jwtService.generateToken(user);
    return LoginResponse.of(token);
  }
}
