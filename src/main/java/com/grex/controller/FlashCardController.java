package com.grex.controller;

import com.grex.dto.FlashCardDto;
import com.grex.dto.GenericMessage;
import com.grex.dto.ProgressDto;
import com.grex.service.CacheService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex")
public class FlashCardController {

    private final CacheService cacheService;

    @Autowired
    public FlashCardController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/flashcard/{groupId}")
    public ResponseEntity<GenericMessage> getGroupById(@PathVariable @NotNull @NotBlank String groupId) {

        FlashCardDto flashCardDto = cacheService.getCachedFlashCardMap().get(groupId);

        if(flashCardDto == null){
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Flashcard not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,flashCardDto), HttpStatus.OK);
    }

}
