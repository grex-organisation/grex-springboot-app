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

    @Cacheable(value = "rankCache", key = "#page + '-' + #pageSize", unless = "#result == null || #result.isEmpty()")
    public List<Rank> getCachedRank(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return rankRepository.findUserRank(pageSize, offset);
    }
}
