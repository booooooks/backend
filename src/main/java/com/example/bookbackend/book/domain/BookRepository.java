package com.example.bookbackend.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT DISTINCT b FROM Book b where b.member = (select m from Member m where m.name = :name)")
    List<Book> findByBookInfo(@Param("name") String name);

    List<Book> findAllByMemberId(Long userId);
}
