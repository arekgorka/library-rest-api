package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.exception.*;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.BorrowingRepository;
import com.crud.library.repository.TitleRepository;
import com.crud.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@SpringBootTest
public class BorrowingServiceTests {

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveBorrowingTest() throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, TitleNotFoundException {
        //Given
        User user = new User("login2","firstname","lastname", LocalDate.now());
        userRepository.save(user);
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        titleRepository.save(title);
        Book book = new Book(title, BookStatus.AVAILABLE);
        bookRepository.save(book);
        Borrowing borrowing = new Borrowing(book,user);
        //When
        borrowingService.startBorrowing(borrowing);
        Borrowing startedBorrowing = borrowingService.getActiveBorrowing(user.getId(), book.getId());
        User savedUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Title savedTitle = titleRepository.findById(title.getId()).orElseThrow(TitleNotFoundException::new);
        Book savedBook = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);
        //then
        assertTrue(borrowingRepository.existsById(startedBorrowing.getId()));
        assertEquals(1,savedUser.getBorrowingsList().size());
        assertEquals(0,savedTitle.getAvailableBooks());
        assertEquals(BookStatus.BORROWED,savedBook.getStatus());
        //CleanUp
        borrowingRepository.deleteById(startedBorrowing.getId());
        bookRepository.deleteById(savedBook.getId());
        titleRepository.deleteById(savedTitle.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void returnBookTest() throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, BookIsNotBorrowedException, TitleNotFoundException {
        //Given
        User user = new User("login2","firstname","lastname", LocalDate.now());
        userRepository.save(user);
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        titleRepository.save(title);
        Book book = new Book(title, BookStatus.AVAILABLE);
        bookRepository.save(book);
        Borrowing borrowing = new Borrowing(book,user);
        borrowingService.startBorrowing(borrowing);
        User savedUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Book savedBook = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);
        Title savedTitle = titleRepository.findById(title.getId()).orElseThrow(TitleNotFoundException::new);
        Borrowing startedBorrowing = borrowingService.getActiveBorrowing(user.getId(), book.getId());
        //Then
        borrowingService.returnBook(savedUser.getId(), savedBook.getId());
        Book returnedBook = bookRepository.findById(savedBook.getId()).orElseThrow(BookNotFoundException::new);
        Title returnedTitle = titleRepository.findById(savedTitle.getId()).orElseThrow(TitleNotFoundException::new);
        Borrowing returnedBorrowing = borrowingRepository.findById(startedBorrowing.getId()).orElseThrow();
        //then
        assertEquals(BookStatus.AVAILABLE,returnedBook.getStatus());
        assertEquals(1,returnedTitle.getAvailableBooks());
        assertEquals(LocalDate.now(),returnedBorrowing.getDateOfReturn());
        //CleanUp
        borrowingRepository.deleteById(returnedBorrowing.getId());
        bookRepository.deleteById(returnedBook.getId());
        titleRepository.deleteById(returnedTitle.getId());
        userRepository.deleteById(savedUser.getId());
    }
}
