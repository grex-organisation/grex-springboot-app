package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.dto.GroupDto;
import com.grex.service.CacheService;
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

    private final CacheService cacheService;

    @Autowired
    public GroupController(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    @GetMapping("/groups")
    public ResponseEntity<GenericMessage> getGroups() {
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, cacheService.getCachedGroupList()), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GenericMessage> getGroupById(@PathVariable @NotNull @NotBlank String groupId) {

        GroupDto groupDto = cacheService.getCachedGroupMap().get(groupId);

        if (groupDto == null) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Group not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, groupDto), HttpStatus.OK);
    }
}

