package com.example.bookbackend.book.application;

import com.example.bookbackend.book.domain.BookMarker;
import com.example.bookbackend.book.domain.BookMarkerRepository;
import com.example.bookbackend.common.exception.GlobalException;
import com.example.bookbackend.common.response.ApiCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkerService {
    private final BookMarkerRepository bookMarkerRepository;
    public BookMarker postBookMarker(BookMarker bookMarker) {
        bookMarkerRepository.save(bookMarker);
        return bookMarker;
    }

    public BookMarker patchBookMarker(BookMarker bookMarker, long bookMarkerId) {
        BookMarker findBookMarker = getBookMarker(bookMarkerId);

        Optional.ofNullable(bookMarker.getDate())
                .ifPresent(findBookMarker::setDate);

        Optional.ofNullable(bookMarker.getText())
                .ifPresent(findBookMarker::setText);

        bookMarkerRepository.save(findBookMarker);
        return bookMarker;
    }

    public BookMarker getBookMarker(long bookMarkerId) {
        Optional<BookMarker> optionalBookMarker = bookMarkerRepository.findById(bookMarkerId);
        BookMarker bookMarker = optionalBookMarker.orElseThrow(() -> new GlobalException(ApiCode.API_0000));
        return bookMarker;
    }

    public List<BookMarker> getBookMarkers() {
        List<BookMarker> bookMarkerList = bookMarkerRepository.findAll();
        return bookMarkerList;
    }

    public void deleteBookMarker(long bookMarkerId) {
        bookMarkerRepository.deleteById(bookMarkerId);
    }

}
