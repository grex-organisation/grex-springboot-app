package com.grex.persistence;

import com.grex.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserAuthenticationRepository {

    @Select("SELECT count(*) > 0 FROM GREX_USER WHERE user_email = #{user_email} OR user_stage_name = #{user_stage_name} ")
    boolean checkEmailOrStageNameAlreadyExists(@Param("user_email") String user_email, @Param("user_stage_name") String user_stage_name);

    @Select("SELECT * FROM GREX_USER WHERE user_email = #{user_email}")
    @Results({
            @Result(property = "username", column = "user_email"),
            @Result(property = "stageName", column = "user_stage_name"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "role", column = "user_role"),
            @Result(property = "isActive", column = "user_is_active"),
            @Result(property = "isBot", column = "user_is_bot")
    })
    public User findByEmail(@Param("user_email") String user_email);

    @Insert("INSERT INTO GREX_USER VALUES( #{user_email}, #{user_stage_name}, #{user_password}, #{user_role}, #{user_is_active},#{user_is_bot} )")
    int addNewUser(@Param("user_email") String user_email, @Param("user_stage_name") String user_stage_name, @Param("user_password") String user_password, @Param("user_role") String user_role, @Param("user_is_active") boolean user_is_active,@Param("user_is_bot") boolean user_is_bot );


 /*   @Delete("DELETE FROM GREX_USER_OTP WHERE user_email = #{user_email}")
    void deleteOtpRecord(@Param("user_email") String user_email);*/
}
