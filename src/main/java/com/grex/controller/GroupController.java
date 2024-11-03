package com.grex.controller;

import com.grex.dto.FlashCardDto;
import com.grex.dto.GenericMessage;
import com.grex.dto.GroupDto;
import com.grex.service.GroupService;
import com.grex.service.UserAuthenticationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex/cdn")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @GetMapping("/groups")
    public ResponseEntity<GenericMessage> getGroups() {

        logger.info("getting groups");

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, groupService.getGroupsFromRedis()), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GenericMessage> getGroupById(@PathVariable @NotNull @NotBlank String groupId) {

        logger.info("getting groups by id"+groupId);

        GroupDto groupDto = groupService.getGroupFromRedis(groupId);

        if (groupDto == null) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Group not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, groupDto), HttpStatus.OK);
    }

    @GetMapping("/flashcard/{groupId}")
    public ResponseEntity<GenericMessage> getFlashCardByGroupId(@PathVariable @NotNull @NotBlank String groupId) {

        logger.info("getting flashcards by group id"+groupId);

        FlashCardDto flashCardDto = groupService.getFlashCardFromRedis(groupId);

        if(flashCardDto == null){
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Flashcard not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,flashCardDto), HttpStatus.OK);
    }
}

