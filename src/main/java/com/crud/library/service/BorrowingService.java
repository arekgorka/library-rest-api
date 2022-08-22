package com.crud.library.service;

import com.crud.library.controller.*;
import com.crud.library.domain.*;
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

    public void startBorrowing(final BorrowingDto borrowingDto) throws Exception {
        User user = userRepository.findById(borrowingDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(borrowingDto.getBookId()).orElseThrow(BookNotFoundException::new);
        LocalDate dateRental = borrowingDto.getDateOfRental();
        if (dateRental.isBefore(LocalDate.now()) || dateRental.isAfter(LocalDate.now())) {
            throw new WrongDateOfRentalException();
        }
        if (book.getStatus().equals(BookStatus.AVAILABLE)) {
            bookRepository.updateBookStatus(book.getId(),BookStatus.BORROWED);
        } else {
            throw new BookNotAvailableException();
        }
        //zobaczyć co z nullem
        Borrowing borrowing = new Borrowing(book,user,dateRental);
        borrowingRepository.save(borrowing);
    }

    public void returnBook(final Long userId, final Long bookId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        LocalDate dateReturn = LocalDate.now();
        Borrowing borrowing = borrowingRepository.getBorrowingByUserAndBookId(user.getId(),book.getId());
        //update bookStatus
        if (book.getStatus().equals(BookStatus.BORROWED)) {
            bookRepository.updateBookStatus(book.getId(),BookStatus.AVAILABLE);
        } else {
            throw new BookIsNotBorrowedException();
        }
        borrowingRepository.updateDateOfReturnBorrowing(borrowing.getId(),dateReturn);
    }

}
