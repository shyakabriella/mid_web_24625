package org.example.service;

import org.example.dao.BookDAO;
import org.example.dao.BorrowerDAO;
import org.example.model.Book;
import org.example.model.Borrower;
import org.example.model.User;

import java.util.Date;

public class BookService {
    private BookDAO bookDAO = new BookDAO();
    private BorrowerDAO borrowerDAO = new BorrowerDAO();

    public boolean borrowBook(User user, Book book) {
        // Check if the book is already borrowed
        if (isBookAvailable(book)) {
            Borrower borrower = new Borrower();
            borrower.setUser(user);
            borrower.setBook(book);
            borrower.setBorrowDate(new Date()); // Set today as borrow date
            borrower.setReturnDate(null); // No return date yet
            borrowerDAO.saveOrUpdateBorrower(borrower); // Use the correct method
            return true;
        }
        return false;
    }

    public boolean returnBook(Borrower borrower) {
        if (borrower != null && borrower.getReturnDate() == null) {
            borrower.setReturnDate(new Date()); // Set today as return date
            borrowerDAO.saveOrUpdateBorrower(borrower); // Use the correct method
            return true;
        }
        return false;
    }

    public boolean isBookAvailable(Book book) {
        // This method should check if the book is currently not borrowed
        return borrowerDAO.findBorrowerByBook(book) == null;
    }

    public double calculateLateFees(Borrower borrower, double dailyFee) {
        if (borrower.getReturnDate() != null) {
            long diff = new Date().getTime() - borrower.getReturnDate().getTime();
            long daysLate = diff / (1000 * 60 * 60 * 24);
            return daysLate * dailyFee;
        }
        return 0.0;
    }
}
