package com.nimbusphagia.mu_libreria.model.entity;

import java.time.LocalDateTime;

import com.nimbusphagia.mu_libreria.model.base.BaseEntity;
import com.nimbusphagia.mu_libreria.model.enums.NewsletterStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Newsletter extends BaseEntity {

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false, length = 10000)
  private String content;

  @Column(nullable = true)
  private LocalDateTime sentAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NewsletterStatus status;

}
