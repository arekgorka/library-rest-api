package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "BORROWINGS")
public class Borrowing {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "DATE_OF_RENTAL")
    private LocalDate dateOfRental;

    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;

    public Borrowing(Book book, User user) {
        this.book = book;
        this.user = user;
        this.dateOfRental = LocalDate.now();
    }
}
