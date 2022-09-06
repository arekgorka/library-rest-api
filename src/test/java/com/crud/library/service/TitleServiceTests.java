package com.crud.library.service;

import com.crud.library.domain.BookStatus;
import com.crud.library.exception.TitleAlreadyExistException;
import com.crud.library.domain.Book;
import com.crud.library.domain.Title;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TitleServiceTests {

    @Autowired
    private TitleService titleService;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void createTitleTest() throws TitleAlreadyExistException {
        //Given
        Title title = new Title("Billy", "Zommer", LocalDate.of(1999,12,10));
        //When
        titleRepository.save(title);
        //Then
        assertTrue(titleRepository.existsById(title.getId()));
        //CleanUp
        titleRepository.deleteById(title.getId());
    }

    @Test
    public void getAvailableBooksByBookTitleTest() throws Exception {
        //Given
        //List<Book> bookList = new ArrayList<>();
        Title title1 = new Title("Billy Summers","Stephen King",LocalDate.of(2019,2,20));
        Title title2 = new Title("ABC","XYZ",LocalDate.of(2019,2,20));
        titleRepository.save(title1);
        titleRepository.save(title2);
        Book book1 = new Book(title1, BookStatus.AVAILABLE);
        Book book2 = new Book(title1, BookStatus.LOST);
        Book book3 = new Book(title1, BookStatus.BORROWED);
        Book book4 = new Book(title2, BookStatus.AVAILABLE);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        //When
        Title titleWithAvailableBooks = titleService.getTitleWithAvailableBooks("Billy Summers");
        //Then
        assertEquals(1L, titleWithAvailableBooks.getAvailableBooks());
        //CleanUp
        bookRepository.deleteById(book1.getId());
        bookRepository.deleteById(book2.getId());
        bookRepository.deleteById(book3.getId());
        bookRepository.deleteById(book4.getId());
        titleRepository.deleteById(title1.getId());
        titleRepository.deleteById(title2.getId());
    }

    @Test
    public void saveTwoTimesSameTitleTest() throws TitleAlreadyExistException {
        //Given
        Title title1 = new Title("Billy Summers", "Stephen King", LocalDate.of(2019,12,10));
        Title title2 = new Title(1L,"Billy Summers", "Stephen King", LocalDate.of(2019,12,10));

        titleRepository.save(title1);
        //When
        try {
            titleService.saveTitle(title2);
        } catch (TitleAlreadyExistException e) {
            System.out.println("Exception: " + e );
        }
        //Then
        assertFalse(titleRepository.existsById(title2.getId()));
        //CleanUp
        titleRepository.deleteById(title1.getId());
    }
}
