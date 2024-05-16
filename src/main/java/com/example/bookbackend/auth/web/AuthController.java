package com.example.bookbackend.auth.web;

import com.example.bookbackend.auth.application.AuthService;
import com.example.bookbackend.auth.application.dto.SignInInfo;
import com.example.bookbackend.token.web.dto.Tokens;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bookbackend.common.util.ResponseManager.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public void signIn(@RequestBody SignInInfo signInInfo, HttpServletResponse response) {
        Tokens tokens = authService.signIn(signInInfo);

        setUpTokensToCookie(tokens, response);
    }
}
