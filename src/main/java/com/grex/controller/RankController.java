package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.model.Rank;
import com.grex.model.User;
import com.grex.service.RankService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/grex")
public class RankController {

    private final RankService rankService;

    @Autowired
    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RankController.class);

    @GetMapping("/ranking")
    public ResponseEntity<GenericMessage> getRank(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "200") int pageSize) {

        logger.info("entered in getRank");

        List<Rank> ranks = rankService.getCachedRank(page, pageSize);

        // Create and return a response
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, ranks), HttpStatus.OK);
    }
}

