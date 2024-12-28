package com.ti9.send.email.core.domain.service.token;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        validateAndRenewToken(tokenDTO);
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
        } catch (IOException e) {
            return e.getMessage().contains("\"code\" : 401");
        }
    }

    private static String renewAccessToken(String refreshToken) throws Exception {
        UserCredentials userCredentials = UserCredentials.newBuilder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(refreshToken)
                .build();
        AccessToken newAccessToken = userCredentials.refreshAccessToken();
        return newAccessToken.getTokenValue();
    }



}
