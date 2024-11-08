package org.cripsy.adminservice.repository;

import org.cripsy.adminservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

    //Admin  find  by ID  SQL Query
    @Query(value = "SELECT * FROM student WHERE id = ?1", nativeQuery = true)
    Admin getAdminById(Integer id);

    Optional<Admin> findById(Integer id);
}
