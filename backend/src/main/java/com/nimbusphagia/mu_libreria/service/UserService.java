package com.nimbusphagia.mu_libreria.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusphagia.mu_libreria.model.dto.request.RegisterRequest;
import com.nimbusphagia.mu_libreria.model.dto.response.UserResponse;
import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.model.enums.UserRole;
import com.nimbusphagia.mu_libreria.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserResponse registerUser(RegisterRequest registerDTO, UserRole role) {
    if (userRepository.existsByEmail(registerDTO.email())) {
      throw new IllegalArgumentException("Email already in use");
    }

    String encodedPassword = passwordEncoder.encode(registerDTO.password());
    User userEntity = registerDTO.toEntity(encodedPassword, role);

    return UserResponse.fromEntity(userRepository.save(userEntity));
  }

}
