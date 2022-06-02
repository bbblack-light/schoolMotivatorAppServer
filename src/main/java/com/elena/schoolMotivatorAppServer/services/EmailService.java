package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;

    }

    public OperationResponse sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("SMA.noreply@yandex.ru");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            return new OperationResponse("ok");
        }
        catch (Exception e) {
            if (e.getMessage().contains("Invalid Addresses"))
                return new OperationResponse("Некорректная почта");
            else return new OperationResponse("Произошла ошибка с почтой");
        }
    }
}
