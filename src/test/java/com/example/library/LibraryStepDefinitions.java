package com.example.library;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryStepDefinitions {

    private BookService bookService;
    private BorrowRecord lastBorrowRecord;
    private RuntimeException lastException;

    @Before
    public void setUp() {
        bookService = new BookService();
    }

    @Given("the library is empty")
    public void the_library_is_empty() {
    }

    @Given("the library has a book with title {string}, author {string}, category {string}")
    public void the_library_has_a_book(String title, String author, String category) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        bookService.addBook(book);
    }

    @Given("the library has {int} books")
    public void the_library_has_n_books(int count) {
        for (int i = 1; i <= count; i++) {
            Book book = new Book();
            book.setTitle("Book " + i);
            book.setAuthor("Author " + i);
            book.setCategory("Category");
            bookService.addBook(book);
        }
    }

    @Given("user {string} has already borrowed {int} books")
    public void user_has_borrowed_n_books(String userId, int count) {
        for (long i = 1; i <= count; i++) {
            bookService.borrowBook(i, userId);
        }
    }

    @Given("user {string} borrows the book with id {int}")
    public void user_borrows_book(String userId, int bookId) {
        lastBorrowRecord = bookService.borrowBook((long) bookId, userId);
    }

    @Given("user {string} has {int} overdue books")
    public void user_has_overdue_books(String userId, int count) {
        for (int i = 0; i < count; i++) {
            Book book = new Book();
            book.setTitle("Overdue Book " + i);
            book.setAuthor("Author");
            book.setCategory("Category");
            Book added = bookService.addBook(book);
            BorrowRecord record = bookService.borrowBook(added.getId(), userId);
            record.setDueDate(LocalDate.now().minusDays(5));
            record.setStatus("Overdue");
            added.setAvailable(false);
        }
    }

    @When("I add a book with title {string}, author {string}, category {string}")
    public void i_add_a_book(String title, String author, String category) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        bookService.addBook(book);
    }

    @When("I get the book with id {int}")
    public void i_get_the_book(int id) {
        bookService.getBookById((long) id);
    }

    @When("I update the book with id {int} to title {string}, author {string}, category {string}")
    public void i_update_the_book(int id, String title, String author, String category) {
        Book updated = new Book();
        updated.setTitle(title);
        updated.setAuthor(author);
        updated.setCategory(category);
        bookService.updateBook((long) id, updated);
    }

    @When("I delete the book with id {int}")
    public void i_delete_the_book(int id) {
        bookService.deleteBook((long) id);
    }

    @When("I try to delete the book with id {int}")
    public void i_try_to_delete_the_book(int id) {
        try {
            bookService.deleteBook((long) id);
        } catch (RuntimeException e) {
            lastException = e;
        }
    }

    @When("user {string} tries to borrow the book with id {int}")
    public void user_tries_to_borrow_book(String userId, int bookId) {
        try {
            lastBorrowRecord = bookService.borrowBook((long) bookId, userId);
        } catch (RuntimeException e) {
            lastException = e;
        }
    }

    @When("user {string} tries to borrow book number {int}")
    public void user_tries_to_borrow_book_number(String userId, int bookId) {
        try {
            lastBorrowRecord = bookService.borrowBook((long) bookId, userId);
        } catch (RuntimeException e) {
            lastException = e;
        }
    }

    @When("user returns the book with id {int}")
    public void user_returns_the_book(int bookId) {
        lastBorrowRecord = bookService.returnBook((long) bookId);
    }

    @Then("the library should have {int} book")
    public void the_library_should_have_one_book(int count) {
        assertEquals(count, bookService.getAllBooks().size());
    }

    @Then("the library should have {int} books")
    public void the_library_should_have_n_books(int count) {
        assertEquals(count, bookService.getAllBooks().size());
    }

    @Then("I should get the book with title {string}")
    public void i_should_get_the_book(String title) {
        Book book = bookService.getBookById(1L);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
    }

    @Then("the book with id {int} should have title {string}")
    public void the_book_should_have_title(int id, String title) {
        Book book = bookService.getBookById((long) id);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
    }

    @Then("the borrow record should have status {string}")
    public void the_borrow_record_should_have_status(String status) {
        assertNotNull(lastBorrowRecord);
        assertEquals(status, lastBorrowRecord.getStatus());
    }

    @Then("I should get an error {string}")
    public void i_should_get_an_error(String errorMessage) {
        assertNotNull(lastException);
        assertEquals(errorMessage, lastException.getMessage());
    }

    @Then("the book with id {int} should be available again")
    public void the_book_should_be_available(int id) {
        Book book = bookService.getBookById((long) id);
        assertNotNull(book);
        assertTrue(book.isAvailable());
    }
}
