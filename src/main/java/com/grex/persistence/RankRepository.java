package com.grex.persistence;

import com.grex.model.Rank;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RankRepository {


    @Select("SELECT * FROM GREX_RANK")
    @Results({
            @Result(property = "rank", column = "rank"),
            @Result(property = "stageName", column = "user_stage_name"),
            @Result(property = "countryCode", column = "user_country"),
            @Result(property = "learnScore", column = "learn_score"),
            @Result(property = "testScore", column = "test_score"),
            @Result(property = "challengeScore", column = "challenge_score"),
            @Result(property = "otherScore", column = "other_score"),
            @Result(property = "totalScore", column = "total_score")

    })
    public List<Rank> findRank();
}
