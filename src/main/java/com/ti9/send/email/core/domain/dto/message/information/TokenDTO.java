package com.ti9.send.email.core.domain.dto.message.information;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String scope;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("id_token")
    private String idToken;

    public static TokenDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TokenDTO.class);
    }

}
