package com.crud.library.domain;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "TITLES")
public class Title {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    //@NotNull
    @Column(name = "BOOKTITLE", unique = true)
    private String bookTitle;

    //@NotNull
    @Column(name = "AUTHOR")
    private String author;

    //@NotNull
    @Column(name = "PUBLICDATE")
    private LocalDate publicDate;

    @OneToMany(
            targetEntity = Book.class,
            mappedBy = "title",
            /*cascade = CascadeType.ALL,*/
            fetch = FetchType.EAGER
    )
    private List<Book> bookList;

    @Column(name = "AVAILABLE_BOOKS")
    private int availableBooks;

    public Title(Long id, String bookTitle, String author, LocalDate publicDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publicDate = publicDate;
    }

    public Title(String bookTitle, String author, LocalDate publicDate) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publicDate = publicDate;
    }

    public int getAvailableBooks() {
        int availableBooks = 0;
        for (Book book : bookList) {
            if (book.getStatus().equals(BookStatus.AVAILABLE)) {
                availableBooks++;
            }
        }
        return availableBooks;
    }
}
