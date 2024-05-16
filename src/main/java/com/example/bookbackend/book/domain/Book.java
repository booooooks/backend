package com.example.bookbackend.book.domain;

import com.example.bookbackend.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BOOK")
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    private String title;
    private String startDate;
    private String endDate;
    private String summary;
    private String text;
    private String imageUrl;
    private int totalPageCount;
    private int goalPageCount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BookMarker> bookMarkerList = new ArrayList<>();

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
}
