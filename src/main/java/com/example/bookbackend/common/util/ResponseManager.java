package com.example.bookbackend.common.util;

import com.example.bookbackend.token.web.dto.Tokens;
import com.example.bookbackend.common.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class ResponseManager {

    public static final String[] TOKEN_TYPE = {"access_token", "refresh_token"};
    public static final int COOKIE_MAX_AGE = 60 * 60;

    public static void setUpTokensToCookie(Tokens tokens, HttpServletResponse response) {
        ResponseCookie accessTokenCookie = createCookie(TOKEN_TYPE[0], tokens.getAccessToken(), false, COOKIE_MAX_AGE);
        ResponseCookie refreshTokenCookie = createCookie(TOKEN_TYPE[1], tokens.getRefreshToken(), true, COOKIE_MAX_AGE);

        setUpCookie(response, accessTokenCookie, refreshTokenCookie);
    }

    private static ResponseCookie createCookie(String tokenType, String token, boolean isHttpOnly, long maxAge) {
        return ResponseCookie.from(tokenType, token)
                .httpOnly(isHttpOnly)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("None")
                .build();
    }

    private static void setUpCookie(HttpServletResponse response, ResponseCookie accessCookie, ResponseCookie refreshCookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    public static void setUpResponse(HttpServletResponse response, CommonResponse commonResponse, HttpStatus httpStatus) {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
            response.setStatus(httpStatus.value());
        } catch (IOException e) {
            log.error("error :: ", e);
            response.setStatus(INTERNAL_SERVER_ERROR.value());
        }
    }
}

