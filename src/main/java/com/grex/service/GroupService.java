package com.grex.service;

import com.grex.configuration.WordConfig;
import com.grex.dto.FlashCardDto;
import com.grex.dto.GroupDto;
import com.grex.model.Group;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final WordConfig wordConfig;

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    public GroupService(WordConfig wordConfig, RedisTemplate<String, Object> redisTemplate) {
        this.wordConfig = wordConfig;
        this.redisTemplate = redisTemplate;
    }

    //load word.yaml data in Redis
    @PostConstruct
    public void init() {

        if (wordConfig.getGroups() != null) {

            // put all group in Redis
            redisTemplate.opsForValue().set("wordConfig:groups", wordConfig.getGroups());

            //put individual group in redis
            for (Group group : wordConfig.getGroups()) {
                redisTemplate.opsForValue().set("wordConfig:" + group.getGroupId(), new GroupDto(group.getGroupId(), group.getGroupName()));
                //redisTemplate.opsForValue().set("flashcard:"+group.getGroupId(), new FlashCardDto(group.getGroupId(), group.getWords()));
            }

            wordConfig.setGroups(null); //free memory
        }
    }

    public List<Group> getGroupsFromRedis() {
        return (List<Group>) redisTemplate.opsForValue().get("wordConfig:groups");
    }

    public GroupDto getGroupFromRedis(String groupId) {
        return (GroupDto) redisTemplate.opsForValue().get("wordConfig:"+groupId);
    }

    public FlashCardDto getFlashCardFromRedis(String groupId) {
        return (FlashCardDto) redisTemplate.opsForValue().get("flashcard:"+groupId);
    }



}
