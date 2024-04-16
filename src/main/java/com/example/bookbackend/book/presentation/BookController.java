package com.example.bookbackend.book.presentation;

import com.example.bookbackend.book.application.BookService;
import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.book.domain.BookMapper;
import com.example.bookbackend.book.dto.BookPostDto;
import com.example.bookbackend.book.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
@Log4j2
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    /*
     * 책 등록
     */
    @PostMapping
    public ResponseEntity<BookResponseDto> postBook(@Validated @RequestBody BookPostDto requestBody) {
        BookResponseDto bookResponseDto;

        Book book = bookMapper.BookPostDtoToBook(requestBody);
        book = bookService.postBook(book);
        bookResponseDto = bookMapper.bookToBookResponseDto(book);

        return ResponseEntity.ok(bookResponseDto);
    }

    /*
     * 책 정보 수정
     */
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> patchBook(@Validated @RequestBody BookPostDto requestBody, @PathVariable long bookId) {
        BookResponseDto bookResponseDto;

        Book book = bookMapper.BookPatchDtoToBook(requestBody);
        book = bookService.patchBook(book, bookId);
        bookResponseDto = bookMapper.bookToBookResponseDto(book);

        return ResponseEntity.ok(bookResponseDto);
    }

    /*
     * 단건 조회
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable long bookId) {
        BookResponseDto bookResponseDto;

        Book book = bookService.getBook(bookId);
        bookResponseDto = bookMapper.bookToBookResponseDto(book);

        return ResponseEntity.ok(bookResponseDto);
    }

    /*
     * 전체 조회
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        List<BookResponseDto> bookResponseDtoList;

        List<Book> bookList = bookService.getBooks();
        bookResponseDtoList = bookMapper.booksToBookListResponseDto(bookList);
        return ResponseEntity.ok(bookResponseDtoList);
    }

    /*
     * BOOK_ID 단위 삭제
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> getBooks(@PathVariable long bookId) {

        bookService.deleteBook(bookId);
        Book book = bookService.getBook(bookId);
        BookResponseDto bookResponseDto = bookMapper.bookToBookResponseDto(book);

        return ResponseEntity.ok(bookResponseDto);
    }
}
