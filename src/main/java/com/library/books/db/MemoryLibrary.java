package com.library.books.db;

import com.library.books.BookNotFoundException;
import com.library.books.models.BooksEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;

@Component
public class MemoryLibrary implements DatabaseInterface {
    // hashmap of string value of title + author to the book entity
    public HashMap<String, BooksEntity> library;

    public MemoryLibrary() {
        this.library = new HashMap<String, BooksEntity>();
    };

    private String libraryHash(String title, String author) {
        String libraryCode = String.format("%s-%s", title, author);
        String id = Base64.getEncoder().encodeToString(libraryCode.getBytes());

        return id;
    }

    // base64 encoded string id
    public BooksEntity getBook(String id) throws BookNotFoundException {
        BooksEntity retrievedBook = library.get(id);
        if (retrievedBook == null) {
            throw new BookNotFoundException("Book not found");
        }
        return retrievedBook;
    };

    public String updateBook(String id, BooksEntity book) {
        // remove the book at the previous id
        try {
            deleteBook(id);
        } catch(BookNotFoundException e) {
            // log and return null
            System.out.printf("while updating: %s", e);
            return null;
        }

        // then add the new book
        String newId = createBook(book);
        return newId;
    };

    public void deleteBook(String id) throws BookNotFoundException {
        // remove the entry from the hashmap
        BooksEntity removedBook = library.remove(id);
        if (removedBook == null) {
            throw new BookNotFoundException("Book not found while attemping to delete");
        }
    };

    public HashMap<String, BooksEntity> getAllBooks() {

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

        String id = libraryHash(book.title, book.author);

        library.putIfAbsent(id, book);

        return id;

    };
}
