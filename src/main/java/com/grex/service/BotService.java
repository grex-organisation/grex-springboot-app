package com.grex.service;

import com.grex.configuration.BotConfig;
import com.grex.dto.BotDto;
import com.grex.model.User;
import com.grex.persistence.BotRepository;
import com.grex.util.OtpGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BotService {

    private final BotRepository botRepository;

    private boolean flag = true;

    private final BotConfig botConfig;

    private final ProgressService progressService;

    @Autowired
    public BotService(BotRepository botRepository, BotConfig botConfig, ProgressService progressService) {
        this.botRepository = botRepository;
        this.botConfig = botConfig;
        this.progressService = progressService;
    }

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);

    @Scheduled(fixedRate = 15 * 60 * 1000)  // 15 mins
    @Transactional
    public void runBot() {

        logger.info("starting bots");

        /*if(flag) {

            logger.info("inserting bot data");

            List<User> botUserList = new ArrayList<>();

            for (BotDto botDto : botConfig.getBots()) {

                String stageName = botDto.getStageName();

                if (stageName.length() > 20) {
                    System.out.println(stageName);
                    stageName = stageName.substring(0, 20); // Trim to exactly 20 characters
                }

               botUserList.add(new User(botDto.getUserName(),botDto.getStageName(), OtpGenerator.generateOtp(),"USER",true,true));
            }


            if (botUserList != null && !botUserList.isEmpty()) {
                botRepository.batchUpdateOrInsertUser(botUserList);
                botRepository.batchUpdateOrInsertProgress();
                botRepository.batchInsertOrUpdateScore(botConfig.getBots());
            }

            flag = !flag;
            logger.info("inserted bot data");

        }*/

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
