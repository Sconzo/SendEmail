package com.ti9.send.email.core.domain.service.token;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Service("gmailTokenService")
public class GmailTokenServiceImpl implements TokenService {

    private static final String CLIENT_ID = "210289579733-6sdm96cu0vlrp47361js8jelr009tk87.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final HttpTransport HTTP_TRANSPORT;
    private static final JsonFactory JSON_FACTORY;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            JSON_FACTORY = JacksonFactory.getDefaultInstance();
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }


    @Override
    public void validateAndRenewToken(TokenDTO tokenDTO) {
        try {
            Credential credential = new GoogleCredential().setAccessToken(tokenDTO.getAccessToken());
            if (isTokenExpired(credential)) {
                tokenDTO.setAccessToken(renewAccessToken(tokenDTO.getRefreshToken()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserInformationDTO getDecodedToken(TokenDTO tokenDTO) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        try (HttpClient client = HttpClient.newHttpClient()) {
            String urlString = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + tokenDTO.getAccessToken();
            URI uri = new URI(urlString);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", "Bearer " + tokenDTO.getAccessToken())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            userInformationDTO = UserInformationDTO.fromJson(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userInformationDTO;
    }

    private static boolean isTokenExpired(Credential credential) {
        try {
            Gmail gmailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("Google API Java Client")
                    .build();
            gmailService.users().getProfile("me").execute();
            return false;
        } catch (TokenResponseException e) {
            return e.getStatusCode() == 401;
        } catch (Exception e) {
            return false;
        }
    }

    private static String renewAccessToken(String refreshToken) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(HTTP_TRANSPORT);
        credentials.setRefreshToken(refreshToken);
        return credentials.getAccessToken();
    }


    private static Credential getCredentials(final NetHttpTransport transport) throws IOException {
        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport,
                GsonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList(GmailScopes.GMAIL_SEND, GmailScopes.MAIL_GOOGLE_COM))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
