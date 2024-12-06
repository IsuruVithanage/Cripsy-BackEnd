package org.cripsy.OrderService.service;

import org.cripsy.OrderService.dto.MailBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tradeasy.official01@gmail.com");
        message.setTo(mailBody.to());
        message.setSubject(mailBody.subject());
        message.setText(mailBody.body());

        mailSender.send(message);
    }
}
