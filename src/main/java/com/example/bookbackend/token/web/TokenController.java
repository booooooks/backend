package com.example.bookbackend.token.web;

import com.example.bookbackend.token.web.dto.Tokens;
import com.example.bookbackend.token.application.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static com.example.bookbackend.common.util.ResponseManager.*;

@RequestMapping("/token")
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public void reissueTokens(@CookieValue(name = "refresh_token") String refreshToken,
                              @RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                              HttpServletResponse response) {

        Tokens tokens = tokenService.reissueTokens(refreshToken, accessToken);

        setUpTokensToCookie(tokens, response);
    }
}
