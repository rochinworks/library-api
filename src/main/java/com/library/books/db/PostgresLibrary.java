package com.library.books.db;

import com.library.books.models.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresLibrary extends JpaRepository<BooksEntity, Long> {

}
