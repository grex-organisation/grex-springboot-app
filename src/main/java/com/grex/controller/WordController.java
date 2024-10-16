package com.grex.controller;


import com.grex.model.Group;
import com.grex.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex/words")
public class WordController {

    @Autowired
    private WordService wordService;

/*
    @GetMapping("/groups")
    public List<Group> getWordsByGroupId() {
        return wordService.getAllGroups();
    }
*/

    @GetMapping("/{groupId}")
    public Group getWordsByGroupId(@PathVariable String groupId) {
        return wordService.getWordsByGroupId(groupId.trim());
    }

}
