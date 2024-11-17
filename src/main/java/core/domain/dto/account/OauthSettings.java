package core.domain.dto.account;

public record OauthSettings(
        String username,
        String password,
        String accessToken,
        String refreshToken
) {
}
