package com.crud.library.controller;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.WrongBookStatusException;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/library/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBook(@RequestBody BookDto bookDto) throws WrongBookStatusException, TitleNotFoundException {
        Book book = bookMapper.mapToBook(bookDto);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{bookId}/{bookStatus}")
    public ResponseEntity<Void> updateBookStatus(@PathVariable Long bookId, @PathVariable String bookStatus)
            throws BookNotFoundException, WrongBookStatusException{
        bookService.updateBookStatus(bookId, bookStatus);
        return ResponseEntity.ok().build();
    }
}
