package com.grex.persistence;

import com.grex.model.Progress;
import com.grex.model.Score;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ScoreRepository {

    @Select("SELECT * FROM GREX_SCORE WHERE user_stage_name = #{user_stage_name}")
    @Results({
            @Result(property = "stageName", column = "user_stage_name"),
            @Result(property = "countryCode", column = "user_country"),
            @Result(property = "learnScore", column = "learn_score"),
            @Result(property = "testScore", column = "test_score"),
            @Result(property = "challengeScore", column = "challenge_score"),
            @Result(property = "otherScore", column = "other_score")
    })
    public Score findScoreByStageName(@Param("user_stage_name") String user_stage_name);

    @Insert("INSERT INTO GREX_SCORE VALUES( #{user_stage_name},#{user_country},0,0,0,0)")
    int makeFirstEntryInScore(@Param("user_stage_name") String user_stage_name, @Param("user_country") String user_country);

    @Update("UPDATE GREX_SCORE SET learn_score = learn_score + 1 WHERE user_stage_name = #{user_stage_name}")
    public void updateLearnScore(@Param("user_stage_name") String user_stage_name);

}
