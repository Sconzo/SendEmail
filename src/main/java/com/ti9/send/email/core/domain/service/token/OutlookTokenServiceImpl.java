package com.ti9.send.email.core.domain.service.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ti9.send.email.core.domain.dto.GenericWrapper;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import com.ti9.send.email.core.domain.dto.account.SmtpSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.model.account.Account;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service("outlookTokenService")
public class OutlookTokenServiceImpl implements TokenService {
    private static final String CLIENT_ID = "a50d2433-5ff2-4884-aefe-3b42e5c7d8f0";
    private static final String CLIENT_SECRET = "AeD8Q~Xrbc6yOhLOBdnGiyKbwbQJHHwQHZIsEa8Q";
    private static final String AUTHORITY = "https://login.microsoftonline.com/46896ccd-0023-43ab-b568-bef36b50aeb1/oauth2/v2.0/token";

    @Override
    public void validateAndRenewToken(GenericWrapper<? extends AccountSettings> accountSettings) {
        try {
            if (isTokenExpired(((OAuthSettings) accountSettings.getValue()).getAccessToken())) {
                ((OAuthSettings) accountSettings.getValue()).update(
                        renewAccessToken(
                                ((OAuthSettings) accountSettings.getValue()).getRefreshToken()
                        )
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserInformationDTO getDecodedToken(Account account) {
        try {
            GenericWrapper<AccountSettings> accountSettingsGeneric = new GenericWrapper<>(account.getAccountSettings());
            this.validateAndRenewToken(accountSettingsGeneric);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://graph.microsoft.com/v1.0/me"))
                    .header(
                            "Authorization",
                            "Bearer " + ((OAuthSettings) accountSettingsGeneric.getValue()).getAccessToken()
                    )
                    .header(
                            "Content-Type",
                            "application/json"
                    )
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return UserInformationDTO.fromJson(response.body());
            } else if (response.statusCode() == 401) {
                throw new RuntimeException("Access token inválido ou expirado.");
            } else {
                throw new RuntimeException("Erro ao acessar a API Graph: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isTokenExpired(String accessToken) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://graph.microsoft.com/v1.0/me"))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 401;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OAuthSettings renewAccessToken(String refreshToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String body = "client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8) +
                "&grant_type=refresh_token";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTHORITY))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), OAuthSettings.class);
        } else {
            throw new RuntimeException("Falha ao renovar o token: " + response.body());
        }
    }
}
