package com.grex.progress.controller;

import com.grex.progress.model.Progress;
import com.grex.progress.service.ProgressService;
import com.grex.user.model.GrexUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grex")
public class ProgressController {

    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/progress")
    public ResponseEntity<Progress> getAllGroupProgressByStageName(@AuthenticationPrincipal final GrexUser currentUser) {
        return ResponseEntity.ok(progressService.findAllGroupProgressByStageName(currentUser.getStageName().trim()));
    }

   @GetMapping("/progress/{groupId}")
    public ResponseEntity<Integer> getSpecificGroupByStageName(@AuthenticationPrincipal final GrexUser currentUser, @PathVariable final String groupId) {
        return ResponseEntity.ok(progressService.findSpecificGroupProgressByStageName(currentUser.getStageName().trim(),groupId.trim()));
    }
}