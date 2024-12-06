package org.cripsy.customerservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findUserByUsername(String username);
    Optional<Customer> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Customer c set c.password = ?2 where c.email = ?1")
    void updatePassword(String email, String password);


    @Query("SELECT COUNT(c) FROM Customer c")
    long getTotalCustomers();


}
