package com.crud.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException exception ) {
        return new ResponseEntity<>("Book with given id doesn't exist.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Object> handleBookNotAvailableException(BookNotAvailableException exception ) {
        return new ResponseEntity<>("Book with given id is not available.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookIsNotBorrowedException.class)
    public ResponseEntity<Object> handleBookIsNotBorrowedException(BookIsNotBorrowedException exception ) {
        return new ResponseEntity<>("Book with given id is not borrowed.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception ) {
        return new ResponseEntity<>("User with given id doesn't exist.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongBookStatusException.class)
    public ResponseEntity<Object> handleWrongBookStatusException(WrongBookStatusException exception ) {
        return new ResponseEntity<>("Book status is not allowed.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongDateOfRentalException.class)
    public ResponseEntity<Object> handleWrongDateOfRentalException(WrongDateOfRentalException exception ) {
        return new ResponseEntity<>("Date of rental is not allowed.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TitleAlreadyExistException.class)
    public ResponseEntity<Object> handleTitleAlreadyExistException(TitleAlreadyExistException exception ) {
        return new ResponseEntity<>("Title already exist.", HttpStatus.BAD_REQUEST);
    }
}
