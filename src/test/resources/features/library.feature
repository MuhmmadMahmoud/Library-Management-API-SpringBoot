Feature: Library Management System

  Scenario: Add a new book
    Given the library is empty
    When I add a book with title "Clean Code", author "Robert Martin", category "Programming"
    Then the library should have 1 book

  Scenario: Get a book by id
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    When I get the book with id 1
    Then I should get the book with title "Clean Code"

  Scenario: Update a book
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    When I update the book with id 1 to title "Refactoring", author "Martin Fowler", category "Programming"
    Then the book with id 1 should have title "Refactoring"

  Scenario: Delete a book
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    When I delete the book with id 1
    Then the library should have 0 books

  Scenario: Borrow a book successfully
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    When user "ahmed" borrows the book with id 1
    Then the borrow record should have status "Borrowed"

  Scenario: Cannot borrow an unavailable book
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    And user "ahmed" borrows the book with id 1
    When user "ali" tries to borrow the book with id 1
    Then I should get an error "Book is not available for borrowing."

  Scenario: Cannot borrow more than 5 books
    Given the library has 6 books
    And user "ahmed" has already borrowed 5 books
    When user "ahmed" tries to borrow book number 6
    Then I should get an error "User cannot borrow more than 5 books."

  Scenario: Cannot borrow when user has 3 or more overdue books
    Given the library has a book with title "New Book", author "Author", category "Category"
    And user "ahmed" has 3 overdue books
    When user "ahmed" tries to borrow the book with id 1
    Then I should get an error "User has 3 or more overdue books. Borrowing blocked."

  Scenario: Return a book successfully
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    And user "ahmed" borrows the book with id 1
    When user returns the book with id 1
    Then the borrow record should have status "Returned"
    And the book with id 1 should be available again

  Scenario: Cannot delete a borrowed book
    Given the library has a book with title "Clean Code", author "Robert Martin", category "Programming"
    And user "ahmed" borrows the book with id 1
    When I try to delete the book with id 1
    Then I should get an error "Cannot delete a book that is currently borrowed."
