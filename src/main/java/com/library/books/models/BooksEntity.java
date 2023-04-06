package com.library.books.models;

import java.util.UUID;

public class BooksEntity {
    public String title;
    public String author;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
