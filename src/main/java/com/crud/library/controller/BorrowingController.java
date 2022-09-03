package com.crud.library.controller;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.BorrowingDto;
import com.crud.library.exception.*;
import com.crud.library.mapper.BorrowingMapper;
import com.crud.library.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/library/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;
    private final BorrowingMapper borrowingMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrowing(@RequestBody BorrowingDto borrowingDto)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException {
        Borrowing borrowing = borrowingMapper.mapToBorrowing(borrowingDto);
        borrowingService.startBorrowing(borrowing);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "return/{userId}/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long userId, @PathVariable Long bookId)
            throws UserNotFoundException, BookNotFoundException, BookIsNotBorrowedException {
        borrowingService.returnBook(userId, bookId);
        return ResponseEntity.ok().build();
    }
    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "return")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        borrowingService.returnBook(userId, bookId);
    }*/
}