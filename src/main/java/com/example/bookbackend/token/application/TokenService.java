package com.example.bookbackend.token.application;

import com.example.bookbackend.token.exception.TokenException;
import com.example.bookbackend.token.web.dto.Tokens;
import com.example.bookbackend.common.jwt.JwtManager;
import com.example.bookbackend.member.domain.Role;
import com.example.bookbackend.token.domain.RefreshToken;
import com.example.bookbackend.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.example.bookbackend.common.response.ApiCode.*;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtManager jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Tokens createTokens(long memberId, Role role) {
        Instant now = Instant.now();
        String accessToken = jwtManager.createAccessToken(memberId, role, now);
        String refreshToken = jwtManager.createRefreshToken(now);

        saveRefreshToken(memberId, refreshToken);

        return Tokens.of(accessToken, refreshToken);
    }

    private void saveRefreshToken(Long memberId, String refreshToken) {
        refreshTokenRepository.findById(memberId)
                .ifPresentOrElse(
                        refresh -> refresh.updateNewToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(memberId, refreshToken))
                );
    }

    @Transactional
    public Tokens reissueTokens(String accessTokens, String refreshToken) {
        Instant now = Instant.now();
        String newRefreshToken = reissueRefreshToken(refreshToken, now);
        String newAccessToken = jwtManager.reissueAccessToken(accessTokens, now);

        return Tokens.of(newAccessToken, newRefreshToken);
    }

    private String reissueRefreshToken(String refreshToken, Instant now) {
        jwtManager.validateRefreshToken(refreshToken);
        RefreshToken entity = findRefreshToken(refreshToken);

        String newRefresh = jwtManager.createRefreshToken(now);

        entity.updateNewToken(newRefresh);
        return newRefresh;
    }

    private RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenException(API_3000));
    }

    public void blockTokens(String accessToken) {
        String[] idAndRole = jwtManager.parseAccessToken(accessToken);
        refreshTokenRepository.deleteById(Long.parseLong(idAndRole[0]));
    }
}
