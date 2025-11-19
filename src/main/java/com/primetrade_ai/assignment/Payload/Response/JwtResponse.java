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

    public JwtResponse(String accessToken, Long id, String username, String email, Boolean isAdmin) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
