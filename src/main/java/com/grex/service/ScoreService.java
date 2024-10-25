package com.grex.service;

import com.grex.model.Score;
import com.grex.persistence.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class ScoreService {}/*{

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void insertScoreEntryByStageName(String stageName,String countryCode){
        scoreRepository.makeFirstEntryInScore(stageName,countryCode);
    }

    public Score getScoreByStageName(String stageName){
        return scoreRepository.findScoreByStageName(stageName);
    }

    public void updateLearnScore(String stageName){
        scoreRepository.updateLearnScore(stageName);
    }

}*/
