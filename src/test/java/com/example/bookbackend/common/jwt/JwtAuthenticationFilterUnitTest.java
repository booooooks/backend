package com.example.bookbackend.common.jwt;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthenticationFilterUnitTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtManager jwtManager;

    @BeforeEach
    void setUp() {
        jwtManager = mock(JwtManager.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtManager);
    }

    @DisplayName("헤더에 토큰이 존재하지 않으면 예외를 발생한다.")
    @Test
    void validateNone() throws IOException, ServletException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        request.setRequestURI("/studies");
        request.addHeader(AUTHORIZATION, "");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Exception exception = (Exception) request.getAttribute("exception");

        // then
        assertThat(exception.getMessage()).isEqualTo("토큰 값이 존재하지 않습니다.");
    }

    @DisplayName("토큰의 타입이 일치하지 않으면 예외를 발생한다.")
    @Test
    void validateTokenType() throws IOException, ServletException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        request.setRequestURI("/studies");
        request.addHeader(AUTHORIZATION, "anytype randomtoken");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Exception exception = (Exception) request.getAttribute("exception");

        // then
        assertThat(exception.getMessage()).isEqualTo("AUTH_TYPE이 일치하지 않습니다. AUTH_TYPE :: anytype");
    }
}
