package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "TITLE_ID")
    private Title title;

    @NotNull
    @Column(name = "STATUS")
    private String status;

    public Book(Title title) {
        this.title = title;
        this.status = BookStatus.AVAILABLE;
    }

    public Book(Title title, String status) {
        this.title = title;
        this.status = status;
    }
}
