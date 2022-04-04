package spring.Entity;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;

    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
