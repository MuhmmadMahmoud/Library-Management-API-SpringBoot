package com.example.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final BookService bookService;

    public SampleDataLoader(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        bookService.addBook(new Book(null, "Clean Code", "Robert Martin", "Programming"));
        bookService.addBook(new Book(null, "Effective Java", "Joshua Bloch", "Java"));
        bookService.addBook(new Book(null, "Spring in Action", "Craig Walls", "Spring Boot"));
        bookService.addBook(new Book(null, "Spring Security in Action", "Laurentiu Spilca", "Security"));
    }
}
