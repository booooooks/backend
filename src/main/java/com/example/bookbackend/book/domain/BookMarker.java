package com.example.bookbackend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BOOK_MARKER")
public class BookMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookMarkerId;
    private String date;
    private String text;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

}
