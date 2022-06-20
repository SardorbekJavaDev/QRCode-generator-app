package com.company.service;

import com.company.youtube_demo.entity.EmailEntity;
import com.company.youtube_demo.enums.EmailType;
import com.company.youtube_demo.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepository emailRepository;

    public void send(String toEmail, String title, String content) {
        SimpleMailMessage simple = new SimpleMailMessage();
        simple.setTo(toEmail);
        simple.setSubject(title);
        simple.setText(content);
        javaMailSender.send(simple);

        EmailEntity entity = new EmailEntity();
        entity.setToEmail(toEmail);
        entity.setType(EmailType.VERIFICATION);
        emailRepository.save(entity);
    }

}
