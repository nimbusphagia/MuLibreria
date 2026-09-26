package com.nimbusphagia.mu_libreria.repository;

import java.util.List;
import java.util.UUID;

import com.nimbusphagia.mu_libreria.model.entity.Book;
import com.nimbusphagia.mu_libreria.repository.base.BaseRepository;

public interface BookRepository extends BaseRepository<Book, Long> {

  Book findByProduct_PublicId(UUID productId);

  List<Book> findAllByProduct_PublicIdIn(List<UUID> productIds);
}
