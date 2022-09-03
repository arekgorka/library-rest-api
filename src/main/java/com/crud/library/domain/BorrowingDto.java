package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BorrowingDto {

    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDate dateOfRental;
    private LocalDate dateOfReturn;

}
