package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GmailServiceInitializer {

    private static final String APPLICATION_NAME = "Your App Name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Gmail getGmailService() throws IOException {
        // Carregue o arquivo de credenciais JSON
        FileInputStream credentialsStream = new FileInputStream("src/main/resources/credentials.json");
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(credentialsStream)
                .createScoped(Collections.singleton(GmailScopes.MAIL_GOOGLE_COM))
                .createDelegated("notificacoes@ti9.com.br");

        // Crie o serviço Gmail autenticado
        return new Gmail.Builder(
                new NetHttpTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(APPLICATION_NAME).build();
    }
}
