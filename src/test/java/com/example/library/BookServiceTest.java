package com.example.library;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    @Test
    public void testAddBook() {
        BookService bookService = new BookService();
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setCategory("Test Category");

        Book addedBook = bookService.addBook(book);

        assertNotNull(addedBook.getId());
        assertEquals("Test Book", addedBook.getTitle());
    }

    @Test
    public void testGetAllBooks() {
        BookService bookService = new BookService();
        bookService.addBook(new Book(null, "Book 1", "Author 1", "Category 1"));
        bookService.addBook(new Book(null, "Book 2", "Author 2", "Category 2"));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    public void testGetBookById() {
        BookService bookService = new BookService();
        Book book = bookService.addBook(new Book(null, "Book 1", "Author 1", "Category 1"));

        Book foundBook = bookService.getBookById(book.getId());

        assertNotNull(foundBook);
        assertEquals("Book 1", foundBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        BookService bookService = new BookService();
        Book book = bookService.addBook(new Book(null, "Book 1", "Author 1", "Category 1"));

        boolean isDeleted = bookService.deleteBook(book.getId());

        assertTrue(isDeleted);
        assertEquals(0, bookService.getAllBooks().size());
    }
}
