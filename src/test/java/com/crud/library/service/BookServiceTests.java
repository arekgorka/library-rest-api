package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookStatus;
import com.crud.library.domain.Title;
import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.WrongBookStatusException;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void saveBookTest() {
        //Given
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        titleRepository.save(title);
        Book book = new Book(title, BookStatus.AVAILABLE);
        //When
        bookService.saveBook(book);
        //Then
        assertTrue(bookRepository.existsById(book.getId()));
        //assertEquals(1,title.getAvailableBooks());
        //CleanUp
        bookRepository.deleteById(book.getId());
        titleRepository.deleteById(title.getId());
    }

    @Test
    public void updateBookStatusTest() throws TitleNotFoundException, BookNotFoundException, WrongBookStatusException {
        //Given
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        titleRepository.save(title);
        Book book = new Book(title, BookStatus.AVAILABLE);
        bookRepository.save(book);
        //When
        bookService.updateBookStatus(book.getId(),BookStatus.BORROWED);
        //Then
        Book bookWithChangedStatus = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);
        assertEquals(BookStatus.BORROWED,bookWithChangedStatus.getStatus());
        //CleanUp
        bookRepository.deleteById(book.getId());
        titleRepository.deleteById(title.getId());
    }
}
