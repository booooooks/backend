package com.example.bookbackend.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkerPatchDto {
    private long bookId;
    private long bookMarkerId;
    private String date;
    private String text;
}
