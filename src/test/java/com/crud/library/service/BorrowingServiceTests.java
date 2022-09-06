package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.exception.BookNotAvailableException;
import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.UserNotFoundException;
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
    public void saveBorrowingTest() throws UserNotFoundException, BookNotFoundException, BookNotAvailableException {
        //Given
        User user = new User("login","firstname","lastname", LocalDate.now());
        userRepository.save(user);
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        titleRepository.save(title);
        Book book = new Book(title, BookStatus.AVAILABLE);
        bookRepository.save(book);
        Borrowing borrowing = new Borrowing(book,user);
        //When
        borrowingRepository.save(borrowing);
        //then
        assertTrue(borrowingRepository.existsById(borrowing.getId()));
        //CleanUp
        borrowingRepository.deleteById(borrowing.getId());
        bookRepository.deleteById(book.getId());
        titleRepository.deleteById(title.getId());
        userRepository.deleteById(user.getId());
    }

    @Test
    public void returnBookTest() {

    }
}
