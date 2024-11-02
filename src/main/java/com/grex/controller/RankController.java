package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.model.Rank;
import com.grex.service.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/grex")
public class RankController {

    private final RankService rankService;

    @Autowired
    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RankController.class);

    @GetMapping("/cdn/ranking")
    public ResponseEntity<GenericMessage> getRank(@RequestParam(defaultValue = "1") int page) {

        logger.info("entered in getRank");
        final int pageSize = 200;

        List<Rank> ranks = rankService.getCachedRank(page, pageSize);

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, ranks), HttpStatus.OK);

    }
}

