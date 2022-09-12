package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String login;
    private String firstname;
    private String lastname;
    private LocalDate created;
}
