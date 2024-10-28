package com.grex.service;

import com.grex.model.Progress;
import com.grex.model.User;
import com.grex.persistence.BotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class BotService {

    private final BotRepository botRepository;

    private final ProgressService progressService;

    @Autowired
    public BotService(BotRepository botRepository, ProgressService progressService) {
        this.botRepository = botRepository;
        this.progressService = progressService;
    }

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);

    //@Scheduled(fixedRate = 60000)  // every minute
   /* @Transactional
    public void runBot() {

        logger.info("starting bots");

        // get a random bot user from grex_user table
        final String  bot_stage_name =  botRepository.getRandomBotUser().getStageName().trim();

        //load progress from table and put in cache
        Progress progress = progressService.findAllGroupProgressByStageName(bot_stage_name);
        cacheService.getProgressMap().put(bot_stage_name,progress);

       //update random column by 1 for random bot
        //cacheService.setCachedProgressByGroup(bot_stage_name,getRandomGroupColumn());

        logger.info("exit bots");

    }*/

    private String getRandomGroupColumn() {
        Random random = new Random();
        int randomNumber = random.nextInt(200) + 1; // Generates a number between 1 and 200
        return "g" + randomNumber;
    }
}
