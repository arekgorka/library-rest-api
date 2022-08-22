package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.Title;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void createBookTest() throws Exception {
        //Given
        List<Book> bookList = new ArrayList<>();
        Title title = Title.builder()
                .bookTitle("Billy Summers")
                .author("Stephen King")
                .publicDate(LocalDate.of(2019,2,20))
                .bookList(bookList)
                .build();
        Book book = Book.builder()
                .title(title)
                .status("available")
                .build();
        bookList.add(book);
        titleRepository.save(title);
        long titleId = title.getId();
        //When
        bookService.saveBook(book);
        long bookId = book.getId();
        //Then
        Assertions.assertEquals(1L, bookRepository.count());
        Assertions.assertEquals(1,title.getBookList().size());
        //CleanUp
        bookRepository.deleteById(bookId);
        titleRepository.deleteById(titleId);
    }
}
