package com.example.bookbackend.reading.application;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.book.domain.BookRepository;
import com.example.bookbackend.common.exception.GlobalException;
import com.example.bookbackend.common.response.ApiCode;
import com.example.bookbackend.reading.application.dto.ReadingRequestDto;
import com.example.bookbackend.reading.application.dto.ReadingResponseDto;
import com.example.bookbackend.reading.domain.Reading;
import com.example.bookbackend.reading.repository.ReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final BookRepository bookRepository;

    //내가 읽은 책 목록 조회
    public List<Reading> getReadingHistory(String name) {
        return readingRepository.findByName(name)
                .orElseThrow(() -> new GlobalException(ApiCode.API_9999));
    }

    //총 읽은 책 개수 계산(진행률이 100%인 책)
    public int getAllBookCounting(String name) {
        //책 계산
        int i = 0;
        Optional<List<Book>> getData = bookRepository.findByMember_Name(name);

        if(getData.isEmpty()) return i;

        for(Book r: getData.get()) {
            if(r.isCompletedReading()) i++;
        }
        return i;
    }

    //읽고 있는 책 개수 계산(진행률이 1% ~ 99%인 책)
    public int getReadingBookCounting(String name) {
        //1. 내가 읽은 책 조회
        int i = 0;
        Optional<List<Book>> getBookData = bookRepository.findByMember_Name(name);

        //2. 책 페이지 조회
        List<Reading> getReadingData = getReadingHistory(name);
        if(getReadingData.isEmpty() || getBookData.isEmpty()) return i; // 아직 읽고 있는 책 없음

        for(Book b: getBookData.get()) {
            for(Reading r : getReadingData) {
                if(b.getTitle().equals(r.getBookTitle())) {
                    if(!b.isCompletedReading())
                        if(r.getPageNo() != 0) i++;
                }
            }
        }
        return i;
    }

    //읽을 책 계산 (진행률이 0%인 책)
    public int getReadBookCounting(String name) {
        //1. 책 페이지 조회
        int i = 0;
        Optional<List<Book>> getBookData = bookRepository.findByMember_Name(name);
        if(getBookData.isEmpty()) return i;

        for(Book b : getBookData.get()) {
            if(!b.isCompletedReading() && b.getTotalPageCount() == 0) {
                i++;
            }
        }

        return i;
    }

    //메인(종합 데이터)
    public ReadingResponseDto returnMainData(ReadingRequestDto readingRequestDto) {
        //1. 책에 대한 정보 가져오기
        List<Book> bookInfo = bookRepository.findByMember_Name(readingRequestDto.getName())
                .orElseThrow(() -> new GlobalException(ApiCode.API_9999));

        //2. 총 읽은 책 계산
        int allBook = getAllBookCounting(readingRequestDto.getName());
        //3. 읽고 있는 책 갯수 계산
        int readingBook = getReadingBookCounting(readingRequestDto.getName());
        //4. 읽을 책 계산
        int readBook = getReadBookCounting(readingRequestDto.getName());

        return ReadingResponseDto.returnData(allBook, readingBook, readBook, bookInfo);
    }
}
