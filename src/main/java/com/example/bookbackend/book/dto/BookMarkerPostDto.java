package com.example.bookbackend.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkerPostDto {
    private long bookId;
    private String date;
    private String text;
}
