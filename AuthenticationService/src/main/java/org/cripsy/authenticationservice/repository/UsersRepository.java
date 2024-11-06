package org.cripsy.authenticationservice.repository;

import org.cripsy.authenticationservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
