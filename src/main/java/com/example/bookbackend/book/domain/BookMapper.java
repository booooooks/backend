package com.example.bookbackend.book.domain;

import com.example.bookbackend.book.dto.BookPostDto;
import com.example.bookbackend.book.dto.BookResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book BookPostDtoToBook(BookPostDto requestBody);
    Book BookPatchDtoToBook(BookPostDto requestBody);
    BookResponseDto bookToBookResponseDto(Book book);

    List<BookResponseDto> booksToBookListResponseDto(List<Book> bookList);
}
