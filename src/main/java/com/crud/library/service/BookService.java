package com.crud.library.service;

import com.crud.library.domain.Title;
import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.exception.WrongBookStatusException;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookStatus;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final TitleRepository titleRepository;

    public void saveBook(final Book book) throws TitleNotFoundException {
            bookRepository.save(book);
            Title title = titleRepository.findById(book.getTitle().getId()).orElseThrow(TitleNotFoundException::new);
            title.setAvailableBooks(bookRepository.countBookByStatusAndTitleId(book.getStatus(), title.getId()));
    }

    public void updateBookStatus (final Long bookId,final String bookStatus) throws
            BookNotFoundException, WrongBookStatusException, TitleNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        updateAmountOfAvailableBooks(book,bookStatus);
        if (bookStatus.equals(BookStatus.AVAILABLE) || bookStatus.equals(BookStatus.BORROWED) || bookStatus.equals(BookStatus.LOST)) {
            bookRepository.updateBookStatus(book.getId(), bookStatus);
        } else {
            throw new WrongBookStatusException();
        }
    }

    public Book findBookById(final Long bookId) throws BookNotFoundException {
         return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public void updateAmountOfAvailableBooks(final Book book, final String bookStatus) throws BookNotFoundException, TitleNotFoundException {
        Book theBook = findBookById(book.getId());
        Title title = titleRepository.findById(theBook.getTitle().getId()).orElseThrow(TitleNotFoundException::new);
        if (theBook.getStatus().equals(BookStatus.AVAILABLE) && bookStatus.equals(BookStatus.BORROWED)) {
            title.setAvailableBooks(title.getAvailableBooks() - 1);
        }
        if (theBook.getStatus().equals(BookStatus.AVAILABLE) && bookStatus.equals(BookStatus.LOST)) {
            title.setAvailableBooks(title.getAvailableBooks() - 1);
        }
        if (theBook.getStatus().equals(BookStatus.BORROWED) && bookStatus.equals(BookStatus.AVAILABLE)) {
            title.setAvailableBooks(title.getAvailableBooks() + 1);
        }
        if (theBook.getStatus().equals(BookStatus.LOST) && bookStatus.equals(BookStatus.AVAILABLE)) {
            title.setAvailableBooks(title.getAvailableBooks() + 1);
        }

    }
}
