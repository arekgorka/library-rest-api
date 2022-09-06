package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookStatus;
import com.crud.library.exception.TitleAlreadyExistException;
import com.crud.library.domain.Title;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.repository.TitleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TitleService {

    @Autowired
    private TitleRepository titleRepository;

    public void saveTitle(final Title title) throws TitleAlreadyExistException {
        if (titleRepository.count()==0) {
            title.setAvailableBooks(0);
            titleRepository.save(title);
        } else if (titleRepository.existsByBookTitleAndAuthor(title.getBookTitle(), title.getAuthor())) {
            throw new TitleAlreadyExistException();
        } else {
            title.setAvailableBooks(0);
            titleRepository.save(title);
        }
    }

    public Title getTitleWithAvailableBooks(final String bookTitle) throws TitleNotFoundException {
        Title foundTitle = titleRepository.findTitleByBookTitle(bookTitle).orElseThrow(TitleNotFoundException::new);
        int availableBooks = foundTitle.getAvailableBooks();
        foundTitle.setAvailableBooks(availableBooks);
        return foundTitle;
    }


    public Title findTitleById(final Long titleId) throws TitleNotFoundException {
        return titleRepository.findById(titleId).orElseThrow(TitleNotFoundException::new);
    }

}


