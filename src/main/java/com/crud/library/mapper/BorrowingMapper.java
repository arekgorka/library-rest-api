package com.crud.library.mapper;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.BorrowingDto;
import com.crud.library.exception.BookNotFoundException;
import com.crud.library.exception.UserNotFoundException;
import com.crud.library.service.BookService;
import com.crud.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingMapper {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    public Borrowing mapToBorrowing(final BorrowingDto borrowingDto) throws BookNotFoundException, UserNotFoundException {
        return new Borrowing(
                bookService.findBookById(borrowingDto.getBookId()),
                userService.findUserById(borrowingDto.getUserId())
        );
    }
}
