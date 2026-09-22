package com.nimbusphagia.mu_libreria.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusphagia.mu_libreria.exception.ConflictException;
import com.nimbusphagia.mu_libreria.model.dto.request.RegisterRequest;
import com.nimbusphagia.mu_libreria.model.entity.User;
import com.nimbusphagia.mu_libreria.model.enums.UserRole;
import com.nimbusphagia.mu_libreria.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User registerUser(RegisterRequest request, UserRole role) {
    if (userRepository.existsByEmail(request.email())) {
      throw new ConflictException("Email already in use");
    }

    String encodedPassword = passwordEncoder.encode(request.password());
    User user = request.toEntity(encodedPassword, role);
    userRepository.save(user);
    return user;
  }

}
