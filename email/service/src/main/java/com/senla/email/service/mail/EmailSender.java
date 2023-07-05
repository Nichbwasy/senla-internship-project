package com.senla.email.service.mail;

import com.senla.email.service.exception.mail.MailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSender {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String[] to, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(text);

        try {
            mailSender.send(message);
            log.info("Mail(s) has been sent successfully from {} to : {}", from, to);
        } catch (Exception e) {
            log.info("Mail sending exception! {}", e.getMessage());
            throw new MailSendingException(
                    String.format("Mail sending exception! %s", e.getMessage())
            );
        }
    }

}
