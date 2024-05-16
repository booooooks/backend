package com.example.bookbackend.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPatchDto {
    private long bookId;
    private long userId;
    private String title;
    private String startDate;
    private String endDate;
    private String summary;
    private String text;
    private String imageUrl;
    private int totalPageCount;
    private int goalPageCount;

}
