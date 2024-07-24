package com.example.bookbackend.reading.application.dto;

import lombok.*;

import java.util.Map;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingResponseDto {
    private int allBookCnt; //총 읽은책 개수
    private int readingBookCnt; //읽고 있는 책 개수
    private int readBookCnt; //읽을 책 개수
    private Map<String, String> progressMap;

    //api 반환
    public static ReadingResponseDto returnData(int allBookCnt, int readingBookCnt, int readBookCnt, Map<String, String> progressMap) {
        return ReadingResponseDto.builder()
                .allBookCnt(allBookCnt)
                .readingBookCnt(readingBookCnt)
                .readBookCnt(readBookCnt)
                .progressMap(progressMap)
                .build();
    }
}
