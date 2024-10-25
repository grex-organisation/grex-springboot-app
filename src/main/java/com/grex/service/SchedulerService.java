package com.grex.service;

import com.grex.controller.ProgressController;
import com.grex.model.Progress;
import com.grex.persistence.SchedulerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SchedulerService {

    private final SchedulerRepository schedulerRepository;

    private final CacheService cacheService;

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    public SchedulerService(SchedulerRepository schedulerRepository, CacheService cacheService) {
        this.schedulerRepository = schedulerRepository;
        this.cacheService = cacheService;
    }

    @Scheduled(fixedRate = 120000)  // keep 3600000 every 1 hour for live and 120000 for dev
    @Transactional
    public void updateRanksScheduler() {

        logger.info("starting scheduler");

        //from cached ProgressMap to progress table.
        List<Progress> progressList = new ArrayList<>(cacheService.getProgressMap().values());
        schedulerRepository.batchUpdateProgress(progressList);

        //from progress table to score table
        schedulerRepository.updateLearnScore();

        //insert from score table to rank table.
        schedulerRepository.updateRanks();

        logger.info("exit scheduler");

    }

}


