package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.dto.GroupDto;
import com.grex.dto.ProgressDto;
import com.grex.model.Progress;
import com.grex.model.User;
import com.grex.service.CacheService;
import com.grex.service.ProgressService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grex")
public class ProgressController {

    private final ProgressService progressService;
    private final CacheService cacheService;

    @Autowired
    public ProgressController(ProgressService progressService, CacheService cacheService) {
        this.progressService = progressService;
        this.cacheService = cacheService;
    }

    @GetMapping("/progress")
    public ResponseEntity<GenericMessage> getAllGroupProgressByStageName(@AuthenticationPrincipal final User currentUser) {
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,progressService.findAllGroupProgressByStageName(currentUser.getStageName().trim())), HttpStatus.OK);
    }

   @GetMapping("/progress/{groupId}")
    public ResponseEntity<GenericMessage> getSpecificGroupByStageName(@AuthenticationPrincipal final User currentUser, @PathVariable @NotNull @NotBlank @NotEmpty final String groupId) {

       if (!cacheService.getCachedGroupMap().containsKey(groupId)) {
           return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Group not found"), HttpStatus.NOT_FOUND);
       }

       byte current_status = progressService.findSpecificGroupProgressByStageName(currentUser.getStageName().trim(),groupId.trim());
       return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, new ProgressDto(groupId,current_status)), HttpStatus.OK);

   }

    @PostMapping("/progress/group/{groupId}/update")
    public void updateGroupProgressStatus(@AuthenticationPrincipal final User currentUser, @PathVariable @NotNull @NotEmpty @NotBlank final String groupId) {
       progressService.updateGroupStatus(currentUser.getStageName(),groupId);
    }

}