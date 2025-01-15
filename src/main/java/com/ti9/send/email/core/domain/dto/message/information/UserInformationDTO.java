package com.ti9.send.email.core.domain.dto.message.information;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDTO {
    private String sub;
    private String name;
    @SerializedName("given_name")
    private String givenName;
    private String picture;
    // Para Microsoft Graph
    @SerializedName("mail")
    private String mail;
    // Para Google
    @SerializedName("email")
    private String email;
    @SerializedName("email_verified")
    private boolean emailVerified;


    public static UserInformationDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserInformationDTO.class);
    }
}
