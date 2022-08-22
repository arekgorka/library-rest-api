package com.crud.library.controller;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/titles")
@RequiredArgsConstructor
public class TitleController {

    private final TitleService titleService;
    private final TitleMapper titleMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody TitleDto titleDto) throws Exception {
        Title title = titleMapper.mapToTitle(titleDto);
        titleService.saveTitle(title);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{bookTitle}")
    public ResponseEntity<List<TitleDto>> getTitlesByBookTitle(@PathVariable String bookTitle) {
       return ResponseEntity.ok(titleMapper.mapToListTitleDto(titleService.getTitles(bookTitle)));
    }

}