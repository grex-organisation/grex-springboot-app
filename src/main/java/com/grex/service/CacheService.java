package com.grex.service;

import com.grex.configuration.WordConfig;
import com.grex.dto.FlashCardDto;
import com.grex.dto.GroupDto;
import com.grex.model.Group;
import com.grex.model.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {

    private final WordConfig wordConfig;

    private Map<String, GroupDto> groupMap = null;
    private List<GroupDto> groupList = null;
    private Map<String, FlashCardDto> FlashCardMap = null;

    private final Map<String, Progress> ProgressMap = new HashMap<>();

    @Autowired
    public CacheService(WordConfig wordConfig) {
        this.wordConfig = wordConfig;
    }

    public Map<String,FlashCardDto> getCachedFlashCardMap() {

        if (FlashCardMap == null || FlashCardMap.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            FlashCardMap = new HashMap<>();
            for (Group group : groups) {
                FlashCardMap.put(group.getGroupId(), new FlashCardDto(group.getGroupId(), group.getWords()));
            }
        }
        return FlashCardMap;
    }

    public Map<String,GroupDto> getCachedGroupMap() {

        if (groupMap == null || groupMap.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            groupMap = new HashMap<>();
            for (Group group : groups) {
                groupMap.put(group.getGroupId(), new GroupDto(group.getGroupId(), group.getGroupName()));
            }
        }
        return groupMap;
    }

    public List<GroupDto> getCachedGroupList() {

        if (groupList == null || groupList.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            groupList = new ArrayList<>();
            for (Group group : groups) {
                groupList.add(new GroupDto(group.getGroupId(), group.getGroupName()));
            }
        }
        return groupList;
    }

}
