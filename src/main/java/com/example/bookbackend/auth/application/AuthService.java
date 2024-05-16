package com.example.bookbackend.auth.application;

import com.example.bookbackend.auth.application.dto.SignInInfo;
import com.example.bookbackend.auth.exception.AuthException;
import com.example.bookbackend.token.web.dto.Tokens;
import com.example.bookbackend.member.application.MemberService;
import com.example.bookbackend.member.domain.Member;
import com.example.bookbackend.token.application.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bookbackend.common.response.ApiCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final MemberService memberService;
    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public Tokens signIn(SignInInfo signInInfo) {
        Member member = memberService.findMemberBy(signInInfo.getEmail());

        validatePassword(signInInfo.getPassword(), member.getPassword());

        return tokenService.createTokens(member.getId(), member.getRole());
    }

    private void validatePassword(String requestPassword, String storedPassword) {
        if (isNotMatch(requestPassword, storedPassword)) {
            throw new AuthException(API_1006);
        }
    }

    private boolean isNotMatch(String requestPassword, String storedPassword) {
        return !passwordEncoder.matches(requestPassword, storedPassword);
    }
}
