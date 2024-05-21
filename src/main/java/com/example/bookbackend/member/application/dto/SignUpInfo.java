package com.example.bookbackend.member.application.dto;

import com.example.bookbackend.member.domain.Member;
import com.example.bookbackend.member.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpInfo {

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String tel;

    @Builder
    private SignUpInfo(String email, String password, String name, String tel) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .tel(tel)
                .role(Role.ROLE_USER)
                .build();
    }
}
