package com.crud.library.repository;

import com.crud.library.domain.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    Book save(Book book);

    @Modifying
    @Query(value = "update BOOKS set status= :bookStatus where id= :bookId")
    void updateBookStatus(Long bookId, String bookStatus);

    long countBookByStatusAndTitleId(String status, Long titleId);
}
