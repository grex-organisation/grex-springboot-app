package com.grex.service;

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

    @Scheduled(fixedRate = 120000)  // every 2 minutes
    @Transactional
    public void runBot() {

        logger.info("starting bots");

        // get a random bot user from grex_user table
        final User  bot_user =  botRepository.getRandomBotUser();

        if(bot_user == null){return;}

        final String bot_stage_name = bot_user.getStageName().trim();

        //load progress from cache and update in cache
        progressService.updateGroupStatus(bot_stage_name,getRandomGroupColumn(),progressService.findAllGroupProgressByStageName(bot_stage_name));
        logger.info("exit bots");

    }

    private String getRandomGroupColumn() {
        Random random = new Random();
        int randomNumber = random.nextInt(200) + 1; // Generates a number between 1 and 200
        return "g" + randomNumber;
    }
}
