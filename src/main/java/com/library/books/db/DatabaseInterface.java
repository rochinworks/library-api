package com.library.books.db;

import com.library.books.BookNotFoundException;
import com.library.books.models.BooksEntity;

import java.util.HashMap;

interface DatabaseInterface {
    BooksEntity getBook(String id) throws BookNotFoundException;
    String updateBook(String id, BooksEntity book) throws BookNotFoundException;
    void deleteBook(String id) throws BookNotFoundException;
    HashMap<String, BooksEntity> getAllBooks();
    String createBook(BooksEntity book);
}
