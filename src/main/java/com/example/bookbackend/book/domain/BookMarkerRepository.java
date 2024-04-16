package com.example.bookbackend.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkerRepository extends JpaRepository<BookMarker, Long> {

}
