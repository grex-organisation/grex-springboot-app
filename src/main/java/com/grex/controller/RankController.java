package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.persistence.RankRepository;
import com.grex.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex")
public class RankController {

    private final RankRepository rankRepository;

    @Autowired
    public RankController(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }


    @GetMapping("/ranking")
    public ResponseEntity<GenericMessage> getRank() {
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, rankRepository.findRank()), HttpStatus.OK);
    }

}

