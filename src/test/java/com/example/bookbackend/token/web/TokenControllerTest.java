package com.example.bookbackend.token.web;

import com.example.bookbackend.common.jwt.JwtAuthenticationFilter;
import com.example.bookbackend.token.application.TokenService;
import com.example.bookbackend.token.web.dto.Tokens;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = TokenController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)}
)
class TokenControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    TokenService tokenService;

    @WithMockUser
    @DisplayName("헤더, 쿠키에서 access, refresh token을 추출해 token을 재발행한다.")
    @Test
    void reissueTokens() throws Exception {
        // given
        String oldAccess ="1234";
        String oldRefresh = "123456";
        Tokens newTokens = Tokens.of("abc", "abcde");

        when(tokenService.reissueTokens(any(), any()))
                .thenReturn(newTokens);

        // when & then
        mockMvc.perform(
                        post("/token/reissue")
                                .cookie(new Cookie("refresh_token", oldRefresh))
                                .header(AUTHORIZATION, oldAccess)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(cookie().value("access_token", newTokens.getAccessToken()))
                .andExpect(cookie().value("refresh_token", newTokens.getRefreshToken()));
    }
}