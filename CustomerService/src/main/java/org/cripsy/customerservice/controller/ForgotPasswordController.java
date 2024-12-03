package org.cripsy.customerservice.controller;

import org.cripsy.customerservice.dto.ChangePassword;
import org.cripsy.customerservice.dto.MailBody;
import org.cripsy.customerservice.model.Customer;
import org.cripsy.customerservice.model.ForgotPassword;
import org.cripsy.customerservice.repository.CustomerRepository;
import org.cripsy.customerservice.repository.ForgotPasswordRepository;
import org.cripsy.customerservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Send mail for email verification
    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        Optional<ForgotPassword> existingOtp = forgotPasswordRepository.findByCustomer(customer);
        existingOtp.ifPresent(forgotPassword -> forgotPasswordRepository.deleteById(forgotPassword.getPasswordId()));

        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("This is the OTP for your Forgot Password Request: " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 5 * 1000)) // 5 minutes
                .customer(customer)
                .build();

        emailService.sendSimpleMail(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok("Email Sent for Verification!");
    }

    // Verify the OTP
    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndCustomer(otp, customer)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email " + email));

        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getPasswordId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP has been verified!");
    }

    // Change password
    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Passwords do not match. Please try again!", HttpStatus.EXPECTATION_FAILED);
        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        customer.setPassword(encodedPassword);
        customerRepository.save(customer); // Update password in the database

        return ResponseEntity.ok("Password has been successfully changed!");
    }

    // OTP Generator
    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
