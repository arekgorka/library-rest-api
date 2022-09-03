package com.crud.library.mapper;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    @Autowired
    private TitleService titleService;

    public Book mapToBook(final BookDto bookDto) throws TitleNotFoundException {
        return new Book(
                bookDto.getId(),
                titleService.findTitleById(bookDto.getTitleId()),
                bookDto.getStatus()
        );
    }

    /*public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle().getId(),
                book.getStatus()
        );
    }*/
}
