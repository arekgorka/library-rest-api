package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.exception.*;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.BorrowingRepository;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void startBorrowing(final Borrowing borrowing)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException {
        User user = userRepository.findById(borrowing.getUser().getId()).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(borrowing.getBook().getId()).orElseThrow(BookNotFoundException::new);
        if (book.getStatus().equals(BookStatus.AVAILABLE)) {
            bookRepository.updateBookStatus(book.getId(),BookStatus.BORROWED);
        } else {
            throw new BookNotAvailableException();
        }
        Borrowing newBorrowing = new Borrowing(book,user);
        borrowingRepository.save(newBorrowing);
    }

    public void returnBook(final Long userId, final Long bookId)
            throws UserNotFoundException, BookNotFoundException, BookIsNotBorrowedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Borrowing borrowing = borrowingRepository.getBorrowingByUserAndBookId(user.getId(),book.getId());
        borrowing.setDateOfReturn(LocalDate.now());
        if (book.getStatus().equals(BookStatus.BORROWED)) {
            bookRepository.updateBookStatus(book.getId(),BookStatus.AVAILABLE);
        } else {
            throw new BookIsNotBorrowedException();
        }
        //borrowingRepository.updateDateOfReturnBorrowing(borrowing.getId(),dateReturn);
    }
}
