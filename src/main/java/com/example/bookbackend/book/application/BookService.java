package com.example.bookbackend.book.application;

import com.example.bookbackend.book.domain.Book;
import com.example.bookbackend.book.domain.BookRepository;
import com.example.bookbackend.common.exception.GlobalException;
import com.example.bookbackend.common.response.ApiCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public Book postBook(Book book) {
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
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new GlobalException(ApiCode.API_0000));
        return book;
    }

    public List<Book> getBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    public void deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
    }

}
