package org.cripsy.authenticationservice.controller;

import org.cripsy.authenticationservice.dto.ChangePassword;
import org.cripsy.authenticationservice.dto.MailBody;
import org.cripsy.authenticationservice.model.ForgotPassword;
import org.cripsy.authenticationservice.model.Users;
import org.cripsy.authenticationservice.repository.ForgotPasswordRepository;
import org.cripsy.authenticationservice.repository.UsersRepository;
import org.cripsy.authenticationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    private UsersRepository usersRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
            private EmailService emailService;

    int otp = otpGenerator();

    //send mail for email verification
    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        Optional<ForgotPassword> existingOtp = forgotPasswordRepository.findByUser(user);
        existingOtp.ifPresent(forgotPassword -> forgotPasswordRepository.deleteById(forgotPassword.getPasswordId()));

        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("This is the OTP for your Forgot Password Request. " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 5 * 1000))
                .user(user)
                .build();

        emailService.sendSimpleMail(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok("Email Sent for Verification!");
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email " + email));

        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getPasswordId());
            return new ResponseEntity<>("OTP has Expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP has been verified!");
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {

        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Please Recheck Passwords!",HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        usersRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
