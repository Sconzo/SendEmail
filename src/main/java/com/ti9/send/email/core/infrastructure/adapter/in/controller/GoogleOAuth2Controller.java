package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.ti9.send.email.core.domain.dto.OAuth2AccessToken;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Map;

@RestController
public class GoogleOAuth2Controller {
    private static final String DONT_TELL = "210289579733-6sdm96cu0vlrp47361js8jelr009tk87.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2/callback/google";
    private static final String SCOPE = "https://www.googleapis.com/auth/gmail.send"; // Escopo para enviar e-mails
    private static final String AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth";

    @GetMapping("/oauth2/authorize/google")
    public RedirectView redirectToGoogleAuthorization() {
        // Construir o URL de autorização
        String url = String.format(
                "%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&access_type=offline",
                AUTHORIZATION_URL,
                DONT_TELL,
                REDIRECT_URI,
                SCOPE
        );

        // Redirecionar o usuário para o Google
        return new RedirectView(url);
    }

    @GetMapping("/oauth2/callback/google")
    public OAuth2AccessToken handleGoogleCallback(@RequestParam("code") String code) {
        System.out.println("Authorization Code: " + code);
        String tokenUrl = "https://oauth2.googleapis.com/token";

        // Construir o corpo da requisição no formato URL-encoded
        String requestBody = UriComponentsBuilder.newInstance()
                .queryParam("code", code)
                .queryParam("client_id", DONT_TELL)
                .queryParam("client_secret", "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")
                .queryParam("redirect_uri", "http://localhost:8080/oauth2/callback/google")
                .queryParam("grant_type", "authorization_code")
                .build()
                .encode()
                .toString()
                .substring(1);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Fazer a chamada HTTP
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            return new OAuth2AccessToken(
                    (String) responseBody.get("access_token"),
                    (String) responseBody.get("refresh_token"),
                    Instant.now().plusSeconds((Integer) responseBody.get("expires_in"))
            );
        }

        // Simples resposta para teste
        return null;
    }
}
