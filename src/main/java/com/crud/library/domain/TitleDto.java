package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class TitleDto {

    private Long id;
    private String bookTitle;
    private String author;
    private LocalDate publicDate;
    private int availableBook;
}
