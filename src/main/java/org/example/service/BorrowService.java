package org.example.service;

import org.example.dao.BookDAO;
import org.example.dao.BorrowerDAO;
import org.example.model.Book;
import org.example.model.Borrower;
import org.example.model.Membership;
import org.example.model.User;

import java.util.Date;
import java.util.List;

public class BorrowService {
    private BookDAO bookDAO = new BookDAO();
    private BorrowerDAO borrowerDAO = new BorrowerDAO();
    private MembershipService membershipService = new MembershipService();

    public boolean borrowBook(User user, Book book) {
//        Membership membership = membershipService.getMembershipDetails(user.getMembership().getId());
//        int borrowedBooksCount = getBorrowedBooksCount(user);

//        if (borrowedBooksCount < membership.getMaxBooksAllowed() && isBookAvailable(book)) {
//            Borrower borrower = new Borrower();
//            borrower.setUser(user);
//            borrower.setBook(book);
//            borrower.setBorrowDate(new Date());
//            borrower.setReturnDate(null);
//            borrowerDAO.saveOrUpdateBorrower(borrower);
//            return true;
//        }
        return false;
    }

    public boolean returnBook(Borrower borrower) {
        if (borrower != null && borrower.getReturnDate() == null) {
            borrower.setReturnDate(new Date());
            borrowerDAO.saveOrUpdateBorrower(borrower);
            return true;
        }
        return false;
    }

    private boolean isBookAvailable(Book book) {
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

    private int getBorrowedBooksCount(User user) {
        List<Borrower> borrowedBooks = borrowerDAO.findBorrowersByUser(user);
        return borrowedBooks.size();  // This should now work if the method is properly defined
    }

}
