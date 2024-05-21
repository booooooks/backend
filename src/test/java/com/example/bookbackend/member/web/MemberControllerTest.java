package com.example.bookbackend.member.web;

import com.example.bookbackend.common.jwt.JwtAuthenticationFilter;
import com.example.bookbackend.common.response.ApiCode;
import com.example.bookbackend.common.response.CommonResponse;
import com.example.bookbackend.member.application.MemberService;
import com.example.bookbackend.member.application.dto.SignUpInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MemberController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)}
)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @WithMockUser
    @DisplayName("회원 가입시 입력 값을 검증한다.")
    @ParameterizedTest
    @MethodSource("argumentsSignUp")
    void signUpTest(SignUpInfo signUpInfo, CommonResponse response) throws Exception {
        // given
        String jsonResponse = objectMapper.writeValueAsString(response);

        // when & then
        mockMvc.perform(post("/member/sign-up")
                        .content(objectMapper.writeValueAsString(signUpInfo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));
    }

    private static Stream<Arguments> argumentsSignUp() {
        return Stream.of(
                Arguments.of(
                        SignUpInfo.builder()
                                .email(null)
                                .password(null)
                                .name(null)
                                .tel("010-1234-1234")
                                .build(),
                        CommonResponse.from(ApiCode.API_9000, Map.of(
                                "email", "이메일은 필수 입력 값 입니다.",
                                "password", "비밀번호는 필수 입력 값 입니다.",
                                "name","이름은 필수 입력 값 입니다."
                        ))
                ),
                Arguments.of(
                        SignUpInfo.builder()
                                .email("emailemailemail")
                                .password("qwer1234")
                                .name("홍길동")
                                .tel("0101234-123")
                                .build(),
                        CommonResponse.from(ApiCode.API_9000, Map.of(
                                "email", "올바른 이메일 형식이 아닙니다.",
                                "tel", "올바른 전화번호 형식이 아닙니다."
                        ))
                )
        );
    }
}