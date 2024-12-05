package com.ti9.send.email.core.domain.service.account;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.ti9.send.email.core.domain.dto.account.AssociateAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Override
    public void associateAccount(AssociateAccountRequest request, MultipartFile file) {
        try {
            final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            GoogleClientSecrets clientSecret = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(file.getInputStream()));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                    .Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecret, Collections.singletonList(GmailScopes.GMAIL_SEND))
                    .setAccessType("offline")
                    .setApprovalPrompt("auto")
                    .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("resource_owner_email");


            // 1. Lê o conteúdo do arquivo JSON
            String jsonContent = new String(file.getBytes());

            // 2. Cria as credenciais usando o conteúdo do JSON
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(jsonContent.getBytes())
            ).createScoped("https://www.googleapis.com/auth/cloud-platform");

            // 3. Inicializa o cliente do Google Cloud usando as credenciais
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();

            // 4. Testa a conexão ao listar buckets (ou outra operação)
            storage.list().iterateAll().forEach(bucket -> {
                System.out.println("Bucket: " + bucket.getName());
            });

            // Exemplo: Associar conta à aplicação
            System.out.println("Conta associada com sucesso!");

        } catch (Exception e) {
            // Trata erros de leitura ou autenticação
            throw new RuntimeException("Falha ao processar o arquivo JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public void listAccounts() {

    }

    @Override
    public void getAccount(UUID id) {

    }

    @Override
    public void updateAccount(UUID id) {

    }

    @Override
    public void deleteAccount(UUID id) {

    }
}
