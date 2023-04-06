package com.library.books;

import com.library.books.db.MemoryLibrary;
import com.library.books.models.BooksEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@RestController
public class BooksController {
    @Autowired
    public MemoryLibrary bookDao;

    private Logger logger;

    public BooksController(MemoryLibrary bookDao) {
        this.bookDao = bookDao;
        this.logger = LoggerFactory.getLogger(BooksController.class);
    }

    @GetMapping("/health")
    public String health() {
        return "Spring boot up and running";
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, BooksEntity> getBooks() {
        HashMap<String, BooksEntity> books = bookDao.getAllBooks();

        return books;
    }

    @GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksEntity getBookById(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            BooksEntity book = bookDao.getBook(id);
            return book;
        } catch(BookNotFoundException e) {
            // TODO: this doesn't return a 400
            logger.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @PostMapping(value = "/books",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String createBook(@RequestBody BooksEntity bookToAdd) {
        String libraryCode = bookDao.createBook(bookToAdd);

        return libraryCode;
    }

    @PutMapping(value = "/books/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateBook(@PathVariable("id") String id, @RequestBody BooksEntity bookToUpdate) {
        String libraryCode = bookDao.updateBook(id, bookToUpdate);

        return libraryCode;
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") String id, HttpServletResponse response) {
        try {

            bookDao.deleteBook(id);
        } catch(BookNotFoundException e) {

            logger.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
