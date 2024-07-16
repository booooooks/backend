package com.example.bookbackend.book.application;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.book.domain.BookRepository;
import com.example.bookbackend.book.exception.BookException;
import com.example.bookbackend.common.response.ApiCode;
import com.example.bookbackend.member.domain.Member;
import com.example.bookbackend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public Book postBook(Book book, long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new BookException("member::"+memberId, ApiCode.API_2000));
        book.setMember(member);
        bookRepository.save(book);
        return book;
    }

    public Book patchBook(Book book, long bookId) {
        Book findBook = getBook(bookId);

        Optional.ofNullable(book.getTitle())
                .ifPresent(findBook::setTitle);

        Optional.of(book.getTotalPageCount())
                .ifPresent(findBook::setTotalPageCount);

        Optional.ofNullable(book.getStartDate())
                .ifPresent(findBook::setStartDate);

        Optional.ofNullable(book.getEndDate())
                .ifPresent(findBook::setEndDate);

        Optional.of(book.getGoalPageCount())
                .ifPresent(findBook::setGoalPageCount);

        Optional.ofNullable(book.getSummary())
                .ifPresent(findBook::setSummary);

        Optional.ofNullable(book.getText())
                .ifPresent(findBook::setText);

        bookRepository.save(findBook);
        return findBook;
    }

    public Book getBook(long bookId) {
        Book book = verifiedBook(bookId);
        return book;
    }

    public List<Book> getBooks(Long userId) {
        List<Book> bookList = bookRepository.findAllByMemberId(userId);
        return bookList;
    }

    public void deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book verifiedBook(long bookId) {
        Optional<Book> optionalBookMarker = bookRepository.findById(bookId);
        Book findBook =
                optionalBookMarker.orElseThrow(() ->
                        new BookException(ApiCode.API_4000));
        return findBook;
    }
}
