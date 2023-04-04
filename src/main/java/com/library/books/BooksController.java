package com.library.books;

import com.library.books.db.MemoryLibrary;
import com.library.books.models.BooksEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

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

    @GetMapping("/books")
    public HashMap<String, BooksEntity> getBooks() {
//        Set<BooksEntity> books = bookDao.getAllBooks();
        HashMap<String, BooksEntity> books = bookDao.getAllBooks();

        return books;
    }

    @GetMapping("/books/{id}")
    public BooksEntity getBookById(@PathVariable("id") String id) {
        BooksEntity book = bookDao.getBook(id);
        if (book == null) {
            logger.error("book was not found");
        }

        return book;
    }

    @PostMapping("/books")
    public String createBook(@RequestBody BooksEntity bookToAdd) {
        String libraryCode = bookDao.createBook(bookToAdd);

        return libraryCode;
    }

//    @PutMapping("/books/{id}")
//    public int updateBook(@PathVariable("id") int id, @RequestBody BooksEntity bookToUpdate) {
//        int libraryCode = bookDao.updateBook(id, bookToUpdate);
//
//        return libraryCode;
//    }

//    @DeleteMapping("/books/{id}")
//    public Boolean deleteBook(int id) {
//        return true;
//    }
}