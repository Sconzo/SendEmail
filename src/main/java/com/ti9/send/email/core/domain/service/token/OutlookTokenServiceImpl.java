package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.application.exceptions.AccessTokenVerificationException;
import com.ti9.send.email.core.application.exceptions.messages.ExceptionMessages;
import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.service.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service("outlookTokenService")
@AllArgsConstructor
public class OutlookTokenServiceImpl implements TokenService {
    private static final String CLIENT_ID = "a50d2433-5ff2-4884-aefe-3b42e5c7d8f0";
    private static final String CLIENT_SECRET = "AeD8Q~Xrbc6yOhLOBdnGiyKbwbQJHHwQHZIsEa8Q";
    private static final String AUTHORITY = "https://login.microsoftonline.com/46896ccd-0023-43ab-b568-bef36b50aeb1/oauth2/v2.0/token";

    private final AccountService accountService;

    @Override
    public Account validateAndRenewToken(Account account) {
        if (isTokenExpired(((OAuthSettings) account.getAccountSettings()).getAccessToken())) {
            String accessToken = renewAccessToken(
                    ((OAuthSettings) account.getAccountSettings()).getRefreshToken()
            );
            account = accountService.updateAccountSettings(account.getId(), accessToken);
        }
        return account;
    }

    @Override
    public UserInformationDTO getDecodedToken(Account account) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            account = this.validateAndRenewToken(account);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://graph.microsoft.com/v1.0/me"))
                    .header(
                            "Authorization",
                            "Bearer " + ((OAuthSettings) account.getAccountSettings()).getAccessToken()
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
                throw new AccessTokenVerificationException(
                        ExceptionMessages.ACCESS_TOKEN_VERIFICATION_ERROR.getMessage()
                );
            } else {
                throw new AccessTokenVerificationException(ExceptionMessages.MICROSOFT_API_ERROR.getMessage());
            }
        } catch (Exception e) {
            throw new AccessTokenVerificationException(ExceptionMessages.MICROSOFT_API_ERROR.getMessage(), e);
        }
    }

    private static boolean isTokenExpired(String accessToken) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://graph.microsoft.com/v1.0/me"))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 401;
        } catch (Exception e) {
            throw new AccessTokenVerificationException(ExceptionMessages.MICROSOFT_API_ERROR.getMessage(), e);
        }
    }

    public String renewAccessToken(String refreshToken) {
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {

            String body = "client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                    "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8) +
                    "&grant_type=refresh_token";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AUTHORITY))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new AccessTokenVerificationException(
                        ExceptionMessages.ERROR_RENEWING_ACCESS_TOKEN_USING_REFRESH_TOKEN.getMessage()
                );
            }
        } catch (Exception e) {
            throw new AccessTokenVerificationException(
                    ExceptionMessages.ERROR_RENEWING_ACCESS_TOKEN_USING_REFRESH_TOKEN.getMessage(),
                    e
            );
        }
    }
}
