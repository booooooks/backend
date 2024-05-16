package com.example.bookbackend.token.repository;

import com.example.bookbackend.token.domain.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @DisplayName("토큰과 일치하는 리프레쉬 토큰 엔티티를 가져온다.")
    @Test
    void findByToken() {
        // given
        String token = "12345";
        RefreshToken refreshToken = new RefreshToken(1L, token);
        refreshTokenRepository.save(refreshToken);

        // when
        RefreshToken findToken = refreshTokenRepository.findByToken(token)
                .orElseThrow();

        // then
        assertThat(findToken.getToken()).isEqualTo(token);
    }
}