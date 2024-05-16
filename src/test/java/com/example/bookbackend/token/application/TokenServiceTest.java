package com.example.bookbackend.token.application;

import com.example.bookbackend.common.jwt.JwtManager;
import com.example.bookbackend.common.response.ApiCode;
import com.example.bookbackend.member.domain.Role;
import com.example.bookbackend.token.domain.RefreshToken;
import com.example.bookbackend.token.exception.TokenException;
import com.example.bookbackend.token.repository.RefreshTokenRepository;
import com.example.bookbackend.token.web.dto.Tokens;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bookbackend.member.domain.Role.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class TokenServiceTest {

    @Autowired
    TokenService tokenService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    JwtManager jwtManager;

    @DisplayName("access, refresh token을 생성할 때 refresh token을 DB에 저장한다.")
    @Test
    void saveRefreshTokenWhenCreateToken() {
        // given
        long memberId = 1L;
        Role role = ROLE_USER;

        // when
        Tokens tokens = tokenService.createTokens(memberId, role);
        RefreshToken refreshToken = refreshTokenRepository.findById(1L)
                .orElseThrow();

        // then
        assertThat(tokens.getRefreshToken()).isEqualTo(refreshToken.getToken());
    }

    @DisplayName("refresh token이 이미 DB에 존재하면 새로운 토큰으로 갱신한다.")
    @Test
    void saveRefreshTokenWhenExist() {
        // given
        long memberId = 1L;
        Role role = ROLE_USER;

        // when
        Tokens oldTokens = tokenService.createTokens(memberId, role);

        // 일시정지 하지 않으면 시간 차이가 없어 결과적으로 같은 refresh token이 생성됨
        sleep(1000);
        Tokens newTokens = tokenService.createTokens(memberId, role);

        RefreshToken refreshToken = refreshTokenRepository.findById(1L)
                .orElseThrow();

        // then
        assertThat(oldTokens.getRefreshToken()).isNotEqualTo(refreshToken.getToken());
        assertThat(newTokens.getRefreshToken()).isEqualTo(refreshToken.getToken());
    }

    @DisplayName("acces token이 만료되면 access, refresh token을 재발행한다.")
    @Test
    void reissuTokens() {
        // given
        long memberId = 1L;
        Role role = ROLE_USER;

        // when
        Tokens oldTokens = tokenService.createTokens(memberId, role);
        sleep(5000);

        // when
        Tokens newTokens = tokenService.reissueTokens(oldTokens.getAccessToken(), oldTokens.getRefreshToken());

        // then
        assertThat(newTokens.getAccessToken()).isNotEqualTo(oldTokens.getAccessToken());
        assertThat(newTokens.getRefreshToken()).isNotEqualTo(oldTokens.getRefreshToken());
    }

    @DisplayName("access, refresh token을 재발행할 때 DB에 저장된 refresh token이 다를 경우 예외가 발생한다.")
    @Test
    void reissuTokensWhenRefreshTokenNotSame() {
        // given
        long memberId = 1L;
        Role role = ROLE_USER;

        // when
        Tokens oldTokens = tokenService.createTokens(memberId, role);
        RefreshToken refreshToken = refreshTokenRepository.findById(1L)
                .orElseThrow();
        refreshToken.updateNewToken("already update token");

        // when & then
        assertThatThrownBy(() -> tokenService.reissueTokens(oldTokens.getAccessToken(), oldTokens.getRefreshToken()))
                .isInstanceOf(TokenException.class)
                .hasMessage(ApiCode.API_3000.getMsg());
    }

    @DisplayName("DB에 저장돼 있는 refresh token을 삭제한다.")
    @Test
    void deleteRefreshTokne() {
        // given
        long memberId = 1L;
        Role role = ROLE_USER;

        // when
        Tokens tokens = tokenService.createTokens(memberId, role);

        // when
        tokenService.blockTokens(tokens.getAccessToken());

        // then
        assertThat(refreshTokenRepository.findById(1L).isEmpty()).isTrue();
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}