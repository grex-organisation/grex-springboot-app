package com.grex.persistence;

import com.grex.model.Progress;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProgressRepository {

    @Select("SELECT * FROM GREX_PROGRESS WHERE user_stage_name = #{user_stage_name}")
    @Results({
            @Result(property = "stageName", column = "user_stage_name"),
    })
    public Progress findGroupsProgressByStageName(@Param("user_stage_name") String user_stage_name);

    @Select("SELECT ${dynamicColumn} FROM GREX_PROGRESS WHERE user_stage_name = #{user_stage_name}")
    public int findSpecificGroupProgressByStageName(@Param("user_stage_name") String user_stage_name, @Param("dynamicColumn") String dynamicColumn);

    @Insert("INSERT INTO GREX_PROGRESS VALUES( #{user_stage_name},0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)")
    int makeFirstEntryInProgress(@Param("user_stage_name") String user_stage_name);


    @Update("UPDATE GREX_PROGRESS SET #{group_id} = #{group_id} + 1 WHERE user_stage_name = #{user_stage_name}")
    public int updateGroupStatus(@Param("user_stage_name") String user_stage_name, @Param("group_id") String group_id);

}
