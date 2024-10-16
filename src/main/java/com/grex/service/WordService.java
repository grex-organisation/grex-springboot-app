package com.grex.service;

import com.grex.configuration.WordConfig;
import com.grex.model.Group;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class WordService {

    private final WordConfig wordConfig;

    public WordService(WordConfig wordConfig) {
        this.wordConfig = wordConfig;
    }

    public List<Group> getAllGroups() {

        List<Group> groups = wordConfig.getGroups();
        List<Group> only_groups = new ArrayList<Group>();

        for (Group group : groups) {
            only_groups.add(new Group(group.getGroupId(), null));
        }
        return only_groups;
    }

    public Group getWordsByGroupId(String groupId) {

        List<Group> groups = wordConfig.getGroups();
        for (Group group : groups) {
            if (group.getGroupId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }
}

