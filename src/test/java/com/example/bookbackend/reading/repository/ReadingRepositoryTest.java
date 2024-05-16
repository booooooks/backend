package com.example.bookbackend.reading.repository;

import com.example.bookbackend.reading.domain.Reading;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
@DataJpaTest
class ReadingRepositoryTest {

    @Autowired
    private ReadingRepository readingRepository;

    @DisplayName("책을 읽기 시작하면 저장한다.")
    @Test
    void saveReading() {
        //given
        Reading reading = createReading("편의점 가는 기분", 40);

        //when
        Reading saveReading = readingRepository.save(reading);

        //then
        assertThat(saveReading.getBookTitle()).isEqualTo("편의점 가는 기분");
    }

    @DisplayName("test라는 사람이 읽은 책 목록을 가져온다.")
    @Test
    void findAllByNickname() {
        //given
        Reading reading1 = createReading("편의점 가는 기분", 20);
        Reading reading2 = createReading("흥부와 놀부", 40);

        //when
        readingRepository.saveAll(List.of(reading1, reading2));

        List<Reading> readings = readingRepository.findAllByName("test");

        //then
        assertThat(readings).hasSize(2)
                .extracting("bookTitle", "name", "pageNo")
                .containsExactlyInAnyOrder(
                        tuple("편의점 가는 기분", "test", 20),
                        tuple("흥부와 놀부", "test", 40)
                );
    }

    public static Reading createReading(String bookTitle, int pageNo) {
        return Reading.builder()
                .bookTitle(bookTitle)
                .name("test")
                .pageNo(pageNo)
                .build();
    }
}