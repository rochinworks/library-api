package com.library.books.db;

import com.library.books.models.BooksEntity;

import java.util.HashMap;
import java.util.Set;

interface DatabaseInterface {
    BooksEntity getBook(String id);
//    String updateBook(String title, String author);
//    void deleteBook(String title, String author);
//    Set<BooksEntity> getAllBooks();
    HashMap<String, BooksEntity> getAllBooks();
    String createBook(BooksEntity book);
}
