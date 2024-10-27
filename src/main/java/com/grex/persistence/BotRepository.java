package com.grex.persistence;

import com.grex.model.User;
import org.apache.ibatis.annotations.*;

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
}
