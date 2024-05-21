package com.example.bookbackend.member.application;

import com.example.bookbackend.member.application.dto.SignUpInfo;
import com.example.bookbackend.member.domain.Member;
import com.example.bookbackend.member.exception.MemberException;
import com.example.bookbackend.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원 가입 시 동일한 이메일이 DB에 존재하면 예외가 발생한다.")
    @Test
    void signUpWhenExistsSameEmail() {
        // given
        memberRepository.save(Member.builder()
                .email("abc@gmail.com")
                .build());

        SignUpInfo signUpInfo = SignUpInfo.builder()
                .email("abc@gmail.com")
                .password("12345")
                .name("abc")
                .build();

        // when & then
        assertThatThrownBy(() -> memberService.saveNewMember(signUpInfo))
                .isInstanceOf(MemberException.class)
                .hasMessage("중복되는 이메일입니다.");
    }

    @DisplayName("이메일로 회원을 조회한다. 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void findByEmail() {
        assertThatThrownBy(() -> memberService.findMemberBy("1234@gmail.com"))
                .isInstanceOf(MemberException.class)
                .hasMessage("일치하는 회원이 없습니다. email :: 1234@gmail.com");
    }
}