package com.grex.persistence;

import com.grex.dto.SignUpUserOtpDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OtpRepository {

    @Insert("INSERT INTO GREX_USER_OTP VALUES( #{user_email}, #{user_stage_name}, #{user_password},#{user_otp})")
    boolean saveOtp(@Param("user_email") String user_email, @Param("user_stage_name") String user_stage_name, @Param("user_password") String user_password, @Param("user_otp") String user_otp);

    @Select("SELECT count(*) > 0 FROM GREX_USER_OTP WHERE user_email = #{user_email}")
    boolean findOtpByEmail(String email);

    @Select("SELECT * FROM GREX_USER_OTP WHERE user_email = #{user_email}")
    @Results({
            @Result(property = "email", column = "user_email"),
            @Result(property = "stageName", column = "user_stage_name"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "otp", column = "user_otp"),
    })
    public SignUpUserOtpDto getSignUpOtp(@Param("user_email") String user_email);

    @Delete("DELETE FROM GREX_USER_OTP WHERE user_email = #{user_email}")
    void deleteOtpRecord(@Param("user_email") String user_email);

}
