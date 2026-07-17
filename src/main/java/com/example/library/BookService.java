package com.example.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    private List<Book> books = new ArrayList<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    
    private long currentBookId = 1;
    private long currentBorrowId = 1;

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
        return null;
    }

    public Book addBook(Book book) {
        logger.info("Adding new book: " + book.getTitle());
        book.setId(currentBookId++);
        book.setAvailable(true);
        books.add(book);
        return book;
    }

    public Book updateBook(Long id, Book updatedBook) {
        logger.info("Updating book with id: " + id);
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setCategory(updatedBook.getCategory());
            return existingBook;
        }
        return null;
    }

    public boolean deleteBook(Long id) {
        logger.info("Deleting book with id: " + id);
        Book book = getBookById(id);
        if (book != null && !book.isAvailable()) {
            throw new RuntimeException("Cannot delete a book that is currently borrowed.");
        }
        
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }

    // Borrow Logic
    public BorrowRecord borrowBook(Long bookId, String userId) {
        logger.info("User " + userId + " attempting to borrow book " + bookId);
        
        Book book = getBookById(bookId);
        if (book == null || !book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing.");
        }

        int activeBorrows = 0;
        int overdueBooks = 0;
        LocalDate today = LocalDate.now();

        for (BorrowRecord record : borrowRecords) {
            if (record.getUserId().equals(userId) && record.getStatus().equals("Borrowed")) {
                if (today.isAfter(record.getDueDate())) {
                    record.setStatus("Overdue");
                    overdueBooks++;
                } else {
                    activeBorrows++;
                }
            } else if (record.getUserId().equals(userId) && record.getStatus().equals("Overdue")) {
                overdueBooks++;
            }
        }

        if (overdueBooks >= 3) {
            throw new RuntimeException("User has 3 or more overdue books. Borrowing blocked.");
        }

        if ((activeBorrows + overdueBooks) >= 5) {
            throw new RuntimeException("User cannot borrow more than 5 books.");
        }

        BorrowRecord newRecord = new BorrowRecord(
                currentBorrowId++,
                bookId,
                userId,
                today,
                today.plusDays(14),
                "Borrowed"
        );
        borrowRecords.add(newRecord);
        book.setAvailable(false);
        
        return newRecord;
    }

    public BorrowRecord returnBook(Long bookId) {
        logger.info("Returning book with id: " + bookId);
        
        for (BorrowRecord record : borrowRecords) {
            if (record.getBookId().equals(bookId) && 
               (record.getStatus().equals("Borrowed") || record.getStatus().equals("Overdue"))) {
                
                record.setReturnDate(LocalDate.now());
                record.setStatus("Returned");
                
                Book book = getBookById(bookId);
                if (book != null) {
                    book.setAvailable(true);
                }
                
                return record;
            }
        }
        throw new RuntimeException("No active borrow record found for this book.");
    }
}
