package com.crud.library.service;

import com.crud.library.exception.TitleAlreadyExistException;
import com.crud.library.domain.Book;
import com.crud.library.domain.Title;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TitleServiceTests {

    //@InjectMocks
    private TitleService titleService;
    //@Mock
    @Autowired
    TitleRepository titleRepository;

    /*@Test
    public void createTitleTest() throws Exception {
        //Given
        List<Book> bookList = new ArrayList<>();
        Title title = Title.builder()
                .id(1L)
                .bookTitle("Billy Summers")
                .author("Stephen King")
                .publicDate(LocalDate.of(2019,2,20))
                .bookList(bookList)
                .build();
        //When
        titleService.saveTitle(title);
        //Then
        verify(titleRepository).save(any(Title.class));
    }*/

    @Test
    public void getTitlesByBookTitleTest() throws Exception {
        //Given
        TitleService titleService = new TitleService(titleRepository);
        List<Book> bookList = new ArrayList<>();
        Title title = Title.builder()
                .bookTitle("Billy Summers")
                .author("Stephen King")
                .publicDate(LocalDate.of(2019,2,20))
                .bookList(bookList)
                .build();
        titleService.saveTitle(title);
        long id = title.getId();
        //When
        List<Title> titleList = titleService.getTitles("Billy Summers");
        //Then
        Assertions.assertEquals(1, titleList.size());
        //CleanUp
        titleRepository.deleteById(id);
    }

    @Test
    public void saveTwoTimesSameTitleTest() throws Exception {
        //Given
        TitleService titleService = new TitleService(titleRepository);
        List<Book> bookList1 = new ArrayList<>();
        List<Book> bookList2 = new ArrayList<>();
        Title title1 = Title.builder()
                .bookTitle("Billy Summers")
                .author("Stephen King")
                .publicDate(LocalDate.of(2019,2,20))
                .bookList(bookList1)
                .build();
        Title title2 = Title.builder()
                .bookTitle("Billy Summers")
                .author("Stephen King")
                .publicDate(LocalDate.of(2019,2,20))
                .bookList(bookList2)
                .build();
        titleRepository.deleteAll();
        titleService.saveTitle(title1);
        long id = title1.getId();
        //When
        try {
            titleService.saveTitle(title2);
        } catch (TitleAlreadyExistException e) {
            System.out.println("Exception: " + e);
        }
        //Then
        Assertions.assertEquals(1L, titleRepository.count());
        //CleanUp
        titleRepository.deleteById(id);
    }
}
