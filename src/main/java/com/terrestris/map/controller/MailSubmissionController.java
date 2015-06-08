package com.terrestris.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dcampbell2 on 3/10/2015.
 */

@RestController
public class MailSubmissionController {

    private final JavaMailSender javaMailSender;

    @Autowired
    MailSubmissionController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping("/mail")
    @ResponseStatus(HttpStatus.CREATED)
    SimpleMailMessage send() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("someone@localhost");
        mailMessage.setReplyTo("someone@localhost");
        mailMessage.setFrom("someone@localhost");
        mailMessage.setSubject("Lorem Ipsum");
        mailMessage.setText("Whatever whatever whatever whatever");
        javaMailSender.send(mailMessage);
        return mailMessage;
    }
}
