package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.exception.*;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.BorrowingRepository;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    @Autowired
    private final BorrowingRepository borrowingRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
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
        Borrowing borrowing = getActiveBorrowing(userId,bookId);
        if (borrowing.getBook().getStatus().equals(BookStatus.BORROWED)) {
            bookRepository.updateBookStatus(borrowing.getBook().getId(),BookStatus.AVAILABLE);
        } else {
            throw new BookIsNotBorrowedException();
        }
        LocalDate dateReturn = LocalDate.now();
        borrowingRepository.updateDateOfReturnBorrowing(borrowing.getId(),dateReturn);
    }

    public Borrowing getActiveBorrowing(final Long userId, final Long bookId) throws UserNotFoundException, BookNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        List<Borrowing> borrowingList = borrowingRepository.findBorrowingByUserIdAndBookId(user.getId(),book.getId()).stream()
                .filter(borrowing -> borrowing.getDateOfReturn() == null)
                .collect(Collectors.toList());
        return borrowingList.get(0);
    }
}
