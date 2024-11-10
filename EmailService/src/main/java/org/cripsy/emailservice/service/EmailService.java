package org.cripsy.emailservice.service;

import jakarta.mail.internet.MimeMessage;
import org.cripsy.emailservice.dto.PayloadDTO;
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

    public ResponseEntity<String> sendEmail(PayloadDTO payload){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(payload.getReceiverAddress());
            helper.setSubject(payload.getSubject());
            helper.setText(payload.getBody(), payload.isHtml());
            helper.setFrom(senderEmail, "Cripsy");
            mailSender.send(message);

            return ResponseEntity.ok("Email Sent") ;
        }

        catch (Exception e){
            System.out.println("Email sending failed" + e);
            return ResponseEntity.status(500).body("Email sending failed");
        }
    }
}
