package com.example.bookbackend.book.presentation;

import com.example.bookbackend.book.application.BookMarkerService;
import com.example.bookbackend.book.domain.BookMarker;
import com.example.bookbackend.book.domain.BookMarkerMapper;
import com.example.bookbackend.book.dto.BookMarkerPostDto;
import com.example.bookbackend.book.dto.BookMarkerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book/marker")
@Log4j2
public class BookMarkerController {
    private final BookMarkerService bookMarkerService;
    private final BookMarkerMapper bookMarkerMapper;

    /*
     * 책갈피 등록
     */
    @PostMapping
    public ResponseEntity<BookMarkerResponseDto> postBookMarker(@Validated @RequestBody BookMarkerPostDto requestBody) {
        BookMarkerResponseDto bookMarkerResponseDto;

        BookMarker bookMarker = bookMarkerMapper.bookMarkerPostDtoToBookMarker(requestBody);

        log.info(bookMarker.toString());
        log.info(bookMarker.getBook().getBookId());
        bookMarker = bookMarkerService.postBookMarker(bookMarker);
        bookMarkerResponseDto = bookMarkerMapper.bookMarkerToBookMarkerResponseDto(bookMarker);

        return ResponseEntity.ok(bookMarkerResponseDto);
    }

    /*
     * 책갈피 수정
     */
    @PatchMapping("/{bookMarkerId}")
    public ResponseEntity<BookMarkerResponseDto> patchBookMarker(@Validated @RequestBody BookMarkerPostDto requestBody, @PathVariable long bookMarkerId) {
        BookMarkerResponseDto bookMarkerResponseDto;

        BookMarker bookMarker = bookMarkerMapper.bookMarkerPatchDtoToBookMarker(requestBody);
        bookMarker = bookMarkerService.patchBookMarker(bookMarker, bookMarkerId);
        bookMarkerResponseDto = bookMarkerMapper.bookMarkerToBookMarkerResponseDto(bookMarker);

        return ResponseEntity.ok(bookMarkerResponseDto);
    }

    /*
     * 책갈피 단건 조회
     */
    @GetMapping("/{bookMarkerId}")
    public ResponseEntity<BookMarkerResponseDto> getBookMarker(@PathVariable long bookMarkerId) {
        BookMarkerResponseDto bookMarkerResponseDto;

        BookMarker bookMarker = bookMarkerService.getBookMarker(bookMarkerId);
        bookMarkerResponseDto = bookMarkerMapper.bookMarkerToBookMarkerResponseDto(bookMarker);

        return ResponseEntity.ok(bookMarkerResponseDto);
    }

    /*
     * 책갈피 전체 조회
     */
    @GetMapping
    public ResponseEntity<List<BookMarkerResponseDto>> getBooks() {
        List<BookMarkerResponseDto> bookMarkerResponseDtoList;

        List<BookMarker> bookMarkerList = bookMarkerService.getBookMarkers();
        bookMarkerResponseDtoList = bookMarkerMapper.bookMarkersToBookMarkerListResponseDto(bookMarkerList);
        return ResponseEntity.ok(bookMarkerResponseDtoList);
    }

    /*
     * 책갈피 ID 단위 삭제
     */
    @DeleteMapping("/{bookMarkerId}")
    public ResponseEntity<BookMarkerResponseDto> getBookMarkers(@PathVariable long bookMarkerId) {

        bookMarkerService.deleteBookMarker(bookMarkerId);
        BookMarker bookMarker = bookMarkerService.getBookMarker(bookMarkerId);
        BookMarkerResponseDto bookMarkerResponseDto = bookMarkerMapper.bookMarkerToBookMarkerResponseDto(bookMarker);

        return ResponseEntity.ok(bookMarkerResponseDto);
    }
}
