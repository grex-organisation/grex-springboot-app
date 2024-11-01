package com.grex.persistence;

import com.grex.dto.BotDto;
import com.grex.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BotRepository {

    @Select("SELECT * FROM GREX_USER  WHERE user_is_bot= true ORDER BY RANDOM() LIMIT 1")
    @Results({
            @Result(property = "username", column = "user_email"),
            @Result(property = "stageName", column = "user_stage_name"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "role", column = "user_role"),
            @Result(property = "isActive", column = "user_is_active"),
            @Result(property = "isBot", column = "user_is_bot")
    })
    public User getRandomBotUser();

    @Insert({
            "<script>",
            "INSERT INTO GREX_USER (user_email, user_stage_name, user_password, user_role, user_is_active, user_is_bot)",
            "VALUES ",
            "<foreach collection='users' item='user' separator=','>",
            "(#{user.username}, #{user.stageName}, #{user.password}, #{user.role}, #{user.isActive}, #{user.isBot})",
            "</foreach>",
            "ON CONFLICT (user_email) DO UPDATE SET",
            "user_stage_name = EXCLUDED.user_stage_name,",
            "user_password = EXCLUDED.user_password,",
            "user_role = EXCLUDED.user_role,",
            "user_is_active = EXCLUDED.user_is_active,",
            "user_is_bot = EXCLUDED.user_is_bot",
            "</script>"
    })
    void batchUpdateOrInsertUser(@Param("users") List<User> users);


    @Insert("INSERT INTO GREX_PROGRESS (user_stage_name) SELECT user_stage_name FROM GREX_USER WHERE user_is_bot = true ON CONFLICT (user_stage_name) DO UPDATE SET user_stage_name = EXCLUDED.user_stage_name")
    void batchUpdateOrInsertProgress();

    @Insert({
            "<script>",
            "INSERT INTO GREX_SCORE (user_stage_name, user_country, learn_score, test_score, challenge_score, other_score)",
            "VALUES ",
            "<foreach collection='users' item='user' separator=','>",
            "(#{user.stageName}, #{user.country}, 0, 0, 0, 0)",
            "</foreach>",
            "ON CONFLICT (user_stage_name) DO UPDATE SET ",
            "user_country = EXCLUDED.user_country, ",
            "learn_score = EXCLUDED.learn_score, ",
            "test_score = EXCLUDED.test_score, ",
            "challenge_score = EXCLUDED.challenge_score, ",
            "other_score = EXCLUDED.other_score",
            "</script>"
    })
    void batchInsertOrUpdateScore(@Param("users") List<BotDto> users);


}
