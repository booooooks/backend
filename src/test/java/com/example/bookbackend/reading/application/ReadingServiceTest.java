package com.example.bookbackend.reading.application;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.book.domain.BookRepository;
import com.example.bookbackend.member.domain.Member;
import com.example.bookbackend.member.domain.Role;
import com.example.bookbackend.member.repository.MemberRepository;
import com.example.bookbackend.reading.application.dto.ReadingRequestDto;
import com.example.bookbackend.reading.application.dto.ReadingResponseDto;
import com.example.bookbackend.reading.domain.Reading;
import com.example.bookbackend.reading.repository.ReadingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ReadingServiceTest {

    @Autowired
    ReadingService readingService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReadingRepository readingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("해당 유저가 읽은 책 기록을 가져온다.")
    @Test
    void getReadingHistory() {
        // Given
        String name = "test";
        List<Reading> readings = readingRepository.findByName(name);

        //when
        List<Reading> result = readingService.getReadingHistory(name);

        //then
        assertEquals(readings, result);
    }

    @DisplayName("현재 읽고 있는 책에 대한 모든 데이터 반환")
    @Test
    void returnMainData() {
        // Given
        ReadingRequestDto readingRequestDto = new ReadingRequestDto();
        readingRequestDto.setName("test");

        Book testBook = createTestData();
        Reading testReading = createReading();
        Member testMember = createMember();

        Member member = memberRepository.save(testMember);
        testBook.setMember(member);
        bookRepository.save(testBook);
        readingRepository.save(testReading);

        List<Book> bookInfo = new ArrayList<>();
        bookInfo.add(testBook);

        int allBook = 0; // 총 읽은 책
        int readingBook = 1; // 읽고 있는 책 갯수
        int readBook = 0; // 읽을 책 계산

        Map<String, String> progressMap = new HashMap<>();
        progressMap.put("편의점 가는 기분", "100%");

        ReadingResponseDto returnData = ReadingResponseDto.returnData(allBook, readingBook, readBook, progressMap);

        // When
        ReadingResponseDto responseDto = readingService.returnMainData(readingRequestDto);

        assertEquals(returnData.getReadingBookCnt(), responseDto.getReadingBookCnt());
    }

    private Book createTestData() {
        Book book = new Book();
        book.setTitle("편의점 가는 기분");
        book.setCompletedReading(false);
        book.setStartDate("2024-02-01");
        book.setEndDate("2024-05-31");
        book.setImageUrl("www.naver.com");
        book.setSummary("편의점 가는 기분");
        book.setText("편의점 가는 기분");
        book.setTotalPageCount(200);
        book.setGoalPageCount(300);
        return book;
    }

    private Member createMember() {
        return Member.builder()
                .email("test@naver.com")
                .name("test")
                .tel("010-0000-0000")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();
    }

    private Reading createReading() {
        return Reading.builder()
                .bookTitle("편의점 가는 기분")
                .name("test")
                .pageNo(20)
                .build();
    }
}