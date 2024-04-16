package com.example.bookbackend.book.domain;

import com.example.bookbackend.book.dto.BookMarkerPostDto;
import com.example.bookbackend.book.dto.BookMarkerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMarkerMapper {
    BookMarker bookMarkerPostDtoToBookMarker(BookMarkerPostDto requestBody);
    BookMarker bookMarkerPatchDtoToBookMarker(BookMarkerPostDto requestBody);
    BookMarkerResponseDto bookMarkerToBookMarkerResponseDto(BookMarker bookMarker);

    List<BookMarkerResponseDto> bookMarkersToBookMarkerListResponseDto(List<BookMarker> bookMarkerList);
}
