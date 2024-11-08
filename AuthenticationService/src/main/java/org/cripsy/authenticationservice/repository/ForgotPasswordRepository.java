package org.cripsy.authenticationservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.authenticationservice.model.ForgotPassword;
import org.cripsy.authenticationservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, Users user);

    Optional<ForgotPassword> findByUser(Users user);
}
