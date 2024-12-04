package org.cripsy.productservice.repository;

import org.cripsy.productservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Transactional
    @Procedure(name = "insertReservedStocks")
    void insertReservedStocks(@Param("valuesToAdd") String values);

    @Query("SELECT t FROM Transaction t WHERE t.createdAt < :expiryTime")
    List<Transaction> findExpiredTransactions(@Param("expiryTime") ZonedDateTime expiryTime);
}
