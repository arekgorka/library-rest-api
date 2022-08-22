package com.crud.library.service;

import com.crud.library.controller.BookNotFoundException;
import com.crud.library.controller.WrongBookStatusException;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookStatus;
import com.crud.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void saveBook(final Book book) throws Exception {
        if (book.getStatus().equals(BookStatus.AVAILABLE)) {
            bookRepository.save(book);
        } else {
            throw new WrongBookStatusException();
        }
    }

    public void updateBookStatus (Long bookId, String bookStatus) throws Exception {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        if (bookStatus.equals(BookStatus.AVAILABLE) || bookStatus.equals(BookStatus.BORROWED) || bookStatus.equals(BookStatus.LOST)) {
            bookRepository.updateBookStatus(book.getId(), bookStatus);
        } else {
            throw new WrongBookStatusException();
        }
    }
}
