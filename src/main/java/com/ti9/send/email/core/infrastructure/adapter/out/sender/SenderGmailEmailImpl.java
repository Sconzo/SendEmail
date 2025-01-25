package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class SenderGmailEmailImpl implements Sender {

//    @Value("${gmail.client.id}")
//    private String clientId;
//    @Value("${gmail.client.secret}")
//    private String clientSecret;
    @Value("${gmail.sender.address}")
    private String from;

    @Override
    public void send(
            List<String> recipientList,
            List<String> ccRecipentList,
            List<String> bccRecipentList,
            String subject,
            String body,
            List<File> fileList,
            AccountSettings accountSettings
    ) {
        try {
            Gmail gmailService = getGmailService();
            MimeMessage email = createEmail(recipientList, "me", subject, body);
            sendMessage(gmailService, email);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    private MimeMessage createEmail(List<String> toList, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        for (String to : toList) {
            email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        }
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private void sendMessage(Gmail gmailService, MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        com.google.api.services.gmail.model.Message message = new Message();
        message.setRaw(encodedEmail);
        gmailService.users().messages().send("me", message).execute();
    }
}
