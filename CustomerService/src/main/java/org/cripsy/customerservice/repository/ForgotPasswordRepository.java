package org.cripsy.customerservice.repository;

import org.cripsy.customerservice.model.Customer;
import org.cripsy.customerservice.model.ForgotPassword;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository {
    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.customer = ?2")
    Optional<ForgotPassword> findByOtpAndCustomer(Integer otp, Customer customer);

    Optional<ForgotPassword> findByCustomer(Customer customer);
}
