package com.ti9.send.email.mock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/google")
    public ResponseEntity<?> initiateGoogleOauth() {
        String authorizationUrl = "https://accounts.google.com/o/oauth2/auth?client_id=958051928246-c9hhh5spkp7j84br1ug5pq5spnnpc5m4.apps.googleusercontent.com&redirect_uri=https://ge.globo.com/&scope=https://www.googleapis.com/auth/gmail.send&response_type=code&access_type=offline";
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(authorizationUrl)).build();
    }

}
