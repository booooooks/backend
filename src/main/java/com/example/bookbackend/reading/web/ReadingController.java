package com.example.bookbackend.reading.web;

import com.example.bookbackend.common.response.ApiCode;
import com.example.bookbackend.common.response.CommonResponse;
import com.example.bookbackend.reading.application.ReadingService;
import com.example.bookbackend.reading.application.dto.ReadingRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/reading")
public class ReadingController {
    private final ReadingService readingService;

    //ping
    @GetMapping("/ping")
    public CommonResponse ping() {
        return new CommonResponse(ApiCode.API_0000.getCode(), ApiCode.API_0000.getMsg());
    }

    //메인페이지 읽은 책 데이터 조회
    @PostMapping("/getReadingData")
    public CommonResponse getReadingData(@Valid @RequestBody ReadingRequestDto readingRequestDto, BindingResult result) {
        //1. 검증오류 체크
        if(result.hasErrors()) {
            log.error("result 검증 오류 : {} ", result.getFieldError().getDefaultMessage());
            return new CommonResponse(ApiCode.API_9999.getCode(), result.getFieldError().getDefaultMessage());
        }
        //2. 로그인 세션 체크 (상의 필요)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            return new CommonResponse(ApiCode.API_9999.getCode(), "로그인 해주세요.");
        }

        //return
        return new CommonResponse(ApiCode.API_0000.getCode(), ApiCode.API_0000.getMsg(), readingService.returnMainData(readingRequestDto));
    }
}
