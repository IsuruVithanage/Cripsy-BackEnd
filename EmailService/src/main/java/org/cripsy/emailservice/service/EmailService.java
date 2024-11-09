package org.cripsy.emailservice.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public ResponseEntity<String> sendEmail(){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("cripsy.app@gmail.com");
            helper.setSubject("This is the mail Subject");
            helper.setText("This is mail Body");
            helper.setFrom(senderEmail, "Cripsy");
            mailSender.send(message);

            return ResponseEntity.ok("Email Sent") ;
        } catch (Exception e){
            System.out.println("Email sending failed" + e);
            return ResponseEntity.status(500).body("Email sending failed");
        }
    }
}
