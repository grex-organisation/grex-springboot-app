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
    public ResponseEntity<GenericMessage> getRank(@AuthenticationPrincipal @NotNull final User currentUser) {

        logger.info("entered in getRank");

        final String stageName = currentUser.getStageName().trim();

        List<Rank> userRank = rankService.getCachedRank(stageName);
        List<Rank> top = rankService.getCachedTopRanks();
        List<Rank> last = rankService.getCachedBottomRanks();

        List<Rank> rankList = new ArrayList<>();

        rankList.addAll(top);
        rankList.addAll(userRank);
        rankList.addAll(last);

        // Create and return a response
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, rankList), HttpStatus.OK);
    }
}

