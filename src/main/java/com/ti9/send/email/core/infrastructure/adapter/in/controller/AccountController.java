package com.ti9.send.email.core.infrastructure.adapter.in.controller;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.OAuth2AccessToken;
import com.ti9.send.email.core.domain.dto.account.AccountRequest;
import com.ti9.send.email.core.domain.dto.account.AccountResponse;
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

    @PostMapping(value = "test", consumes = "application/json")
    public OAuth2AccessToken test(
            @RequestHeader String authorization,
            @RequestParam("clientId") String clientId,
            @RequestParam("clientSecret") String clientSecret
    ) throws MessagingException {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        String body = UriComponentsBuilder.newInstance()
                .queryParam("refresh_token", "1//0hdYr4err3YoECgYIARAAGBESNwF-L9IrZ0AYkx3hrjmUsoZpZLbQXFr0KF1YoNlUzJgdZlNkLpgcJg9AcOLbKocBpEEeAGbYuKw")
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

    @PostMapping()
    public DataWrapper<AccountResponse> createAccount(
            @RequestHeader String authorization,
            @RequestBody AccountRequest body
    ) {

        DataWrapper<AccountResponse> dataWrapper = accountService.createAccount(body);
        dataWrapper.setMessage("Account creation success.");
        dataWrapper.setStatus(HTTPResponse.SC_CREATED);
        return dataWrapper;
    }

    @GetMapping("/list")
    public DataListWrapper<AccountResponse> listAccounts(
            @RequestHeader String authorization
    ) {
        DataListWrapper<AccountResponse> dataListWrapper = accountService.listAccounts();
        dataListWrapper.setMessage("Success.");
        dataListWrapper.setStatus(HTTPResponse.SC_OK);
        return dataListWrapper;
    }

    @GetMapping("{uuid}")
    public DataWrapper<AccountResponse> getAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<AccountResponse> dataWrapper = accountService.getAccount(uuid);
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/{uuid}")
    public DataWrapper<AccountResponse> updateAccount(
            @RequestHeader String authorization,
            @RequestBody AccountRequest body,
            @PathVariable UUID uuid
    ) {
        DataWrapper<AccountResponse> dataWrapper = accountService.updateAccount(uuid, body);
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/status/{uuid}")
    public DataWrapper<AccountResponse> changeStatus(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.changeStatus(uuid);
        DataWrapper<AccountResponse> dataWrapper = new DataWrapper<>();
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @DeleteMapping("/{uuid}")
    public DataWrapper<AccountResponse> deleteAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.deleteAccount(uuid);
        DataWrapper<AccountResponse> dataWrapper = new DataWrapper<>();
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }
}
