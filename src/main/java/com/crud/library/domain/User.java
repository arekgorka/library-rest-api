package com.crud.library.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "CREATED")
    private LocalDate created;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "user",
            /*cascade = CascadeType.ALL,*/
            fetch = FetchType.EAGER
    )
    private List<Borrowing> borrowingsList;

    public User(Long id, String login, String firstname, String lastname) {
        this.id = id;
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = LocalDate.now();
    }

    public User(String login, String firstname, String lastname, LocalDate created) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = created;
    }
}
