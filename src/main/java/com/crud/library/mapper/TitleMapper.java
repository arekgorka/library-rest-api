package com.crud.library.mapper;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.service.TitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleMapper {


    private TitleService titleService;

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getBookTitle(),
                titleDto.getAuthor(),
                titleDto.getPublicDate()
        );
    }

    public TitleDto mapToTitleDtoWithAvailableBooks(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getBookTitle(),
                title.getAuthor(),
                title.getPublicDate(),
                title.getAvailableBooks()
        );
    }


    /*public List<TitleDto> mapToListTitleDto(final List<Title> titles) {
        return titles.stream()
                .map(this::mapToTitleDto)
                .collect(Collectors.toList());
    }*/
}
