package com.grex.service;

import com.grex.model.Rank;
import com.grex.persistence.RankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankService {

    private final RankRepository rankRepository;
    private static final Logger logger = LoggerFactory.getLogger(RankService.class);

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Cacheable(value = "rankCache", key = "'topRanks'", unless = "#result == null || #result.isEmpty()")
    public List<Rank> getCachedTopRanks() {
        logger.info("Entering getCachedTopRanks");
        return rankRepository.findFirstTopTenRank();
    }

    @Cacheable(value = "rankCache", key = "'bottomRanks'", unless = "#result == null || #result.isEmpty()")
    public List<Rank> getCachedBottomRanks() {
        logger.info("Entering getCachedBottomRanks");
        return rankRepository.findLastTopTenRank();
    }

    public List<Rank> getCachedRank(String stageName) {
        return rankRepository.findUserRank(stageName);

    }
}