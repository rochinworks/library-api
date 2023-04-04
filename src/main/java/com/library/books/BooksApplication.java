package com.library.books;

import com.library.books.db.MemoryLibrary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BooksApplication.class, args);
	}
}
