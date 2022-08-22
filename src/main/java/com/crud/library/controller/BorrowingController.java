package com.crud.library.controller;

import com.crud.library.domain.BorrowingDto;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrowing(@RequestBody BorrowingDto borrowingDto) throws Exception {
        borrowingService.startBorrowing(borrowingDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "return/{userId}/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long userId, @PathVariable Long bookId) throws Exception {
        borrowingService.returnBook(userId, bookId);
        return ResponseEntity.ok().build();
    }
    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "return")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        borrowingService.returnBook(userId, bookId);
    }*/
}