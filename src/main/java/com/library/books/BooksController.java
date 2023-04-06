package com.library.books;

import com.library.books.db.MemoryLibrary;
import com.library.books.db.PostgresLibrary;
import com.library.books.models.BooksEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

@RestController
public class BooksController {
    @Autowired
    public MemoryLibrary bookDao;

    @Autowired
    public PostgresLibrary persistentBookDao;

    private Logger logger;

    public BooksController(MemoryLibrary bookDao, PostgresLibrary persistentBookDao) {
        this.bookDao = bookDao;
        this.persistentBookDao = persistentBookDao;
        this.logger = LoggerFactory.getLogger(BooksController.class);
    }

    @GetMapping("/health")
    public String health() {
        return "Spring boot up and running";
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
//    public HashMap<String, BooksEntity> getBooks() {
      public List<BooksEntity> getBooks() {
//        HashMap<String, BooksEntity> books = bookDao.getAllBooks();
        List<BooksEntity> books = persistentBookDao.findAll();

        return books;
    }

    @GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksEntity getBookById(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            BooksEntity book = bookDao.getBook(id);
            return book;
        } catch(BookNotFoundException e) {
            logger.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @PostMapping(value = "/books",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String createBook(@RequestBody BooksEntity bookToAdd, HttpServletResponse response) {
        if (bookToAdd == null) {
            logger.error("book was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (bookToAdd.author == null ) {
            logger.error("author was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (bookToAdd.title == null) {
            logger.error("title was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        String libraryCode = bookDao.createBook(bookToAdd);

        return libraryCode;
    }

    @PutMapping(value = "/books/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateBook(@PathVariable("id") String id, @RequestBody BooksEntity bookToUpdate, HttpServletResponse response) {
        if (bookToUpdate == null) {
            logger.error("book was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (bookToUpdate.author == null ) {
            logger.error("author was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (bookToUpdate.title == null) {
            logger.error("title was null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

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
