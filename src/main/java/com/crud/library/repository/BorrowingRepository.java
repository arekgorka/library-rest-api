package com.crud.library.repository;

import com.crud.library.domain.Borrowing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {

    @Override
    Borrowing save(Borrowing borrowing);

    /*@Modifying
    @Query(value = "update BORROWINGS set dateOfReturn= :dateOfReturn where id= :borrowingId")
    void updateDateOfReturnBorrowing(Long borrowingId, LocalDate dateOfReturn);*/


    Borrowing findBorrowingByUserIdAndBookId(Long userId, Long bookId); //check
}
