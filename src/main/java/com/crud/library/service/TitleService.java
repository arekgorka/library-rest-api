package com.crud.library.service;

import com.crud.library.exception.TitleAlreadyExistException;
import com.crud.library.domain.Title;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.repository.TitleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TitleService {

    private TitleRepository titleRepository;

    public void saveTitle(final Title title) throws TitleAlreadyExistException {
        if (titleRepository.existsByBookTitleAndAuthor(title.getBookTitle(), title.getAuthor())) {
            throw new TitleAlreadyExistException();
        } else {
            titleRepository.save(title);
        }
    }

    public List<Title> getTitles(final String bookTitle) {
        return titleRepository.findTitlesByBookTitle(bookTitle);
    }

    public Title findTitleById(final Long titleId) throws TitleNotFoundException {
        return titleRepository.findById(titleId).orElseThrow(TitleNotFoundException::new);
    }
}


