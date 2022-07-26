package com.crud.library.service;

import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.WrongBookStatusException;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookStatus;
import com.crud.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public void saveBook(final Book book) {
            bookRepository.save(book);
    }

    public void updateBookStatus (final Long bookId,final String bookStatus) throws
            BookNotFoundException, WrongBookStatusException {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        if (bookStatus.equals(BookStatus.AVAILABLE) || bookStatus.equals(BookStatus.BORROWED) || bookStatus.equals(BookStatus.LOST)) {
            bookRepository.updateBookStatus(book.getId(), bookStatus);
        } else {
            throw new WrongBookStatusException();
        }
    }

    public Book findBookById(final Long bookId) throws BookNotFoundException {
         return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }
}
