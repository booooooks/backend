package com.example.bookbackend.reading.application.dto;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.reading.domain.Reading;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingResponseDto {
    private int allBookCnt; //총 읽은책 개수
    private int readingBookCnt; //읽고 있는 책 개수
    private int readBookCnt; //읽을 책 개수
    private List<Book> books; //목표, 책 제목, 책 내용?, 읽은 페이지(%), 이미지, 완독여부

    //api 반환
    public static ReadingResponseDto returnData(int allBookCnt, int readingBookCnt, int readBookCnt, List<Book> books) {
        return ReadingResponseDto.builder()
                .allBookCnt(allBookCnt)
                .readingBookCnt(readingBookCnt)
                .readBookCnt(readBookCnt)
                .books(books)
                .build();
    }
}
