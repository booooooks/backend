package com.example.bookbackend.reading.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingRequestDto {
    //이건 유저정보에서 조회하는거라 솔직히 필요없음.
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
}
