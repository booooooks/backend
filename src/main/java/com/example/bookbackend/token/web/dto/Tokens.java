package com.example.bookbackend.token.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Tokens {

    private String accessToken;
    private String refreshToken;

    private Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Tokens of(String accessToken, String refreshToken) {
        return new Tokens(accessToken, refreshToken);
    }
}
