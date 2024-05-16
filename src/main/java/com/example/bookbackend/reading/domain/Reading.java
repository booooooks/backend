package com.example.bookbackend.reading.domain;

import com.example.bookbackend.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "reading_history")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reading extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk

    private String bookTitle; //책이름

    private String name; //유저 닉네임

    private int pageNo; //페이지 수
}