package com.library.books.models;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bid;
    public String title;
    public String author;
    public Long getBid() {
        return bid;
    }
    public void setBid(Long bid) {
        this.bid = bid;
    }
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
