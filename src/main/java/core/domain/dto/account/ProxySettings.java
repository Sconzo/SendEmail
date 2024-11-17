package core.domain.dto.account;

public record ProxySettings (
        String server,
        String port,
        String username,
        String password,
        String protocol
) {
}
