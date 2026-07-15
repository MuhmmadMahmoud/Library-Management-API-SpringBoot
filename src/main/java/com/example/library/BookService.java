package com.example.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    private List<Book> books = new ArrayList<>();
    private long currentId = 1;

    public List<Book> getAllBooks() {
        logger.info("Getting all books");
        return books;
    }

    public Book getBookById(Long id) {
        logger.info("Getting book with id: " + id);
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null; // Return null if not found
    }

    public Book addBook(Book book) {
        logger.info("Adding new book: " + book.getTitle());
        book.setId(currentId++);
        books.add(book);
        return book;
    }

    public boolean deleteBook(Long id) {
        logger.info("Deleting book with id: " + id);
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }
}
