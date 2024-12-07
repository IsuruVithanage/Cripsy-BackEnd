package org.cripsy.adminservice.repository;

import org.cripsy.adminservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
    Admin findAdminByName(String username);
}
