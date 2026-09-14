package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.base.BaseEntity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book extends BaseEntity {
  @Column(nullable = false)
  private String title;

  @Column(nullable = true)
  private String synopsis;

  @ElementCollection
  @CollectionTable(name = "book_images", joinColumns = @JoinColumn(name = "book_id"))
  private List<String> images;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private Author author;

  @Column(unique = true, nullable = false)
  private String isbn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "publisher_id")
  private Publisher publisher;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;
}
