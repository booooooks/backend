package com.example.bookbackend.member.web;

import com.example.bookbackend.member.application.MemberService;
import com.example.bookbackend.member.application.dto.SignUpInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpInfo signUpInfo) {
        memberService.saveNewMember(signUpInfo);
    }
}
