package com.library.books.db;

import com.library.books.models.BooksEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MemoryLibrary implements DatabaseInterface {
    // hashmap of string value of title + author to the book entity
    public HashMap<String, BooksEntity> library;

    public MemoryLibrary() {
        this.library = new HashMap<String, BooksEntity>();
    };

    // base64 encoded string id
    public BooksEntity getBook(String id) {
        BooksEntity retrievedBook = library.get(id);
        if (retrievedBook == null) {
            // TODO: log this

        }
        return retrievedBook;
    };

//    public int updateBook(int id, BooksEntity book) {
//        // TODO: find book in hashmap, modify, put back in map
//        return "";
//    };
//    public void deleteBook(String title, String author) {};
    public HashMap<String, BooksEntity> getAllBooks() {
//        Set<BooksEntity> allBooks = library.values().stream().collect(Collectors.toSet());

        return library;
    };

    public String createBook(BooksEntity book) {
        if (book == null) {
            // TODO: log this
            return null;
        }

        if (book.author == null ) {
            // TODO: log this
            return null;
        }
        if (book.title == null) {
            // TODO: log this
            return null;
        }

        String libraryCode = String.format("%s-%s", book.title, book.author);
        String id = Base64.getEncoder().encodeToString(libraryCode.getBytes());

        library.put(id, book);

        return id;

    };
}
