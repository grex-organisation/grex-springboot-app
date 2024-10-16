package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.model.User;
import com.grex.service.ProgressService;
import com.grex.model.Progress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grex")
public class ProgressController {

    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/progress")
    public ResponseEntity<Progress> getAllGroupProgressByStageName(@AuthenticationPrincipal final User currentUser) {
        return ResponseEntity.ok(progressService.findAllGroupProgressByStageName(currentUser.getStageName().trim()));
    }

   @GetMapping("/progress/{groupId}")
    public ResponseEntity<Integer> getSpecificGroupByStageName(@AuthenticationPrincipal final User currentUser, @PathVariable final String groupId) {
        return ResponseEntity.ok(progressService.findSpecificGroupProgressByStageName(currentUser.getStageName().trim(),groupId.trim()));
    }

    @PostMapping("/group/{groupId}/update")
    public ResponseEntity<GenericMessage> updateGroupProgressStatus(@PathVariable @NotNull @NotEmpty @NotBlank String groupId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        GenericMessage response = new GenericMessage(HttpStatus.OK,progressService.updateGroupStatus(currentUser.getStageName(),groupId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}