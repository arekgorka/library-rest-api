package com.crud.library.service;

import com.crud.library.controller.TitleAlreadyExistException;
import com.crud.library.domain.Title;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    public Title saveTitle(final Title title) throws Exception {
        if (titleRepository.existsByBookTitleAndAuthor(title.getBookTitle(), title.getAuthor())) {
            throw new TitleAlreadyExistException();
        } else {
            return titleRepository.save(title);
        }
    }

    public List<Title> getTitles(final String bookTitle) {

        return titleRepository.findTitlesByBookTitle(bookTitle);
    }
}
