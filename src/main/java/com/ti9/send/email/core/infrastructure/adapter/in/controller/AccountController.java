package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.sun.mail.smtp.SMTPTransport;
import com.ti9.send.email.core.domain.dto.OAuth2AccessToken;
import com.ti9.send.email.core.domain.service.account.AccountService;
import jakarta.mail.*;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.mail.internet.MimeMessage;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

//    @PostMapping(consumes = "multipart/form-data")
//    public void associateAccount(
//            @RequestHeader String authorization,
//            @RequestPart("file") MultipartFile file,
//            @RequestPart("request") AssociateAccountRequest request
//    ) {
//        accountService.associateAccount(request, file);
//    }

    @PostMapping(consumes = "application/json")
    public OAuth2AccessToken test(
            @RequestHeader String authorization,
            @RequestParam("clientId") String clientId,
            @RequestParam("clientSecret") String clientSecret
    ) throws MessagingException {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        String body = UriComponentsBuilder.newInstance()
                .queryParam("refresh_token", "1//0hUVdKj5n7NbhCgYIARAAGBESNwF-L9IrXywWa0cJShJN53jr5Ph-oLAAKcys7lupYvtEmE8AZ6cKoWHegH84k4qvv7S6vhcarVI")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "refresh_token")
                .build()
                .encode()
                .toString()
                .substring(1);
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // Fazer a chamada HTTP
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);


        String accessToken = (String) response.getBody().get("access_token");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Ou outro servidor
        mailSender.setPort(587);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2"); // Necessário para OAuth2

        Session session2 = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // O e-mail e o token são passados para autenticação
                return new PasswordAuthentication("emailsend346@gmail.com", accessToken);
            }
        });

        Session session = Session.getInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom("costing013@gmail.com");
        message.setRecipients(Message.RecipientType.TO, "rspolydoro@gmail.com");
        message.setSubject("subject");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setText(body, true);

        Transport transport = session.getTransport("smtp");
        // If the password is non-null, SMTP tries to do AUTH LOGIN.
        transport.connect("smtp.gmail.com", "costing013@gmail.com", accessToken);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        if (response.getStatusCode() == HttpStatus.OK) {
            // Processar a resposta e criar o objeto OAuth2AccessToken
            Map<String, Object> responseBody = response.getBody();
            return new OAuth2AccessToken(
                    (String) responseBody.get("access_token"),
                    null, // O refresh_token não é retornado na atualização
                    Instant.now().plusSeconds((Integer) responseBody.get("expires_in"))
            );
        }

        throw new RuntimeException("Erro ao atualizar o access_token");

    }


    @GetMapping("/list")
    public void listAccounts(
            @RequestHeader String authorization
    ) {
        accountService.listAccounts();
    }

    @GetMapping("{uuid}")
    public void getAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.getAccount(uuid);
    }

    @PatchMapping("/{uuid}")
    public void updateAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.updateAccount(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.deleteAccount(uuid);
    }
}
