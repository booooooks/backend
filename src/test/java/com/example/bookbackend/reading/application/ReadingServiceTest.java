package com.example.bookbackend.reading.application;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.reading.repository.ReadingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ReadingServiceTest {

    @Autowired
    ReadingService readingService;

    @Autowired
    ReadingRepository readingRepository;

    @DisplayName("해당 유저가 읽고있는 책이 몇권인지 가져온다.")
    @Test
    void getReadingHistory() {

    }

    @DisplayName("해당 유저가 총 읽은 책이 몇권인지 가져온다.")
    @Test
    void getAllBookCounting() {
    }

    @DisplayName("")
    @Test
    void getReadingBookCounting() {
    }

    @DisplayName("")
    @Test
    void getReadBookCounting() {
    }

    @DisplayName("")
    @Test
    void returnMainData() {
    }

    private Book createTestData() {
        Book book = new Book();
        book.setTitle("편의점 가는 기분");
        book.setCompletedReading(true);
        book.setStartDate("2024-02-01");
        book.setEndDate("2024-05-31");
        book.setImageUrl("www.naver.com");
        book.setSummary("편의점 가는 기분");
        book.setText("편의점 가는 기분");
        book.setTotalPageCount(300);
        book.setGoalPageCount(300);
        return book;
    }
}