package com.nimbusphagia.mu_libreria.model.entity;

import com.nimbusphagia.mu_libreria.model.dto.request.BookDetailsRequest;
import com.nimbusphagia.mu_libreria.model.entity.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
  @Column(nullable = false)
  private String title;

  @Column(nullable = true)
  private String synopsis;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private Author author;

  @Column(unique = true, nullable = false)
  private String isbn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "publisher_id", nullable = false)
  private Publisher publisher;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  @Builder.Default
  private Set<Genre> genres = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;

  public void updateFrom(BookDetailsRequest details, Author author, Publisher publisher, Set<Genre> genres) {
    this.title = details.title();
    this.synopsis = details.synopsis();
    this.author = author;
    this.isbn = details.isbn();
    this.publisher = publisher;
    this.genres = genres;
  }
}
