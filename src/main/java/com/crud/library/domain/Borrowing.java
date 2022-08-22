package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "BORROWINGS")
public class Borrowing {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "DATE_OF_RENTAL")
    private LocalDate dateOfRental;

    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;

    //construktor który ma User Book i rental

    public Borrowing(Book book, User user, LocalDate dateOfRental) {
        this.book = book;
        this.user = user;
        this.dateOfRental = dateOfRental;
    }

    public Borrowing(Book book, User user, LocalDate dateOfRental, LocalDate dateOfReturn) {
        this.book = book;
        this.user = user;
        this.dateOfRental = dateOfRental;
        this.dateOfReturn = dateOfReturn;
    }
}
