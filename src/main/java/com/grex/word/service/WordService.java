package com.grex.word.service;

import com.grex.word.config.WordConfig;
import com.grex.word.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WordService {

    private final WordConfig wordConfig;

    public WordService(WordConfig wordConfig) {
        this.wordConfig = wordConfig;
    }


/*    public List<Group> getAllGroups() {

        List<Group> groups = wordConfig.getGroups(); // Get the list of groups
        List<Group> only_groups = new ArrayList<Group>();

        for (Group group : groups) { // Iterate over each group
            only_groups.add(new Group(group.getGroupId(), null));
        }
        return only_groups;
    }*/

    public Group getWordsByGroupId(String groupId) {

        List<Group> groups = wordConfig.getGroups(); // Get the list of groups

        for (Group group : groups) { // Iterate over each group
            if (group.getGroupId().equals(groupId)) { // Check if the groupId matches
                //return group.getWords(); // Return the list of words for the matching group
                return group;
            }
        }
        return null; // Return null if no matching group is found
    }
}

