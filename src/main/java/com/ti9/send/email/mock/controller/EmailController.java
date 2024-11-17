package com.ti9.send.email.mock.controller;


import com.ti9.send.email.mock.domain.SendEmailRequest;
import com.ti9.send.email.mock.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody SendEmailRequest request) {
        try {
            emailService.sendEmail(request.recipients(), request.subject(), request.template());
            return "Emails enviados com sucesso!";
        } catch (MessagingException e) {
            return "Falha ao enviar e-mail: " + e.getMessage();
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
