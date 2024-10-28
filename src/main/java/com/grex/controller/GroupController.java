package com.grex.controller;

import com.grex.dto.FlashCardDto;
import com.grex.dto.GenericMessage;
import com.grex.dto.GroupDto;
import com.grex.service.GroupService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping("/groups")
    public ResponseEntity<GenericMessage> getGroups() {
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, groupService.getGroupsFromRedis()), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GenericMessage> getGroupById(@PathVariable @NotNull @NotBlank String groupId) {

        GroupDto groupDto = groupService.getGroupFromRedis(groupId);

        if (groupDto == null) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Group not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, groupDto), HttpStatus.OK);
    }

    @GetMapping("/flashcard/{groupId}")
    public ResponseEntity<GenericMessage> getFlashCardByGroupId(@PathVariable @NotNull @NotBlank String groupId) {

        FlashCardDto flashCardDto = groupService.getFlashCardFromRedis(groupId);

        if(flashCardDto == null){
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Flashcard not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,flashCardDto), HttpStatus.OK);
    }
}

