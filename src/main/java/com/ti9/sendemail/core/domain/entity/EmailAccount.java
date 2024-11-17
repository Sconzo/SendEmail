package com.ti9.sendemail.core.domain.entity;

import com.ti9.sendemail.core.domain.enums.Provider;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EmailAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private Provider provider;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
