package com.primetrade_ai.assignment.Payload.Response;


import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Boolean isAdmin;
    private String avatar;

    public JwtResponse(String accessToken, Long id, String username, String email, Boolean isAdmin, String avatar) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
        this.avatar = avatar;
    }
}
