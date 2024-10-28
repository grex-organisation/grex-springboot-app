package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.dto.ProgressDto;
import com.grex.model.Progress;
import com.grex.model.User;
import com.grex.service.ProgressService;
import com.grex.util.ProgressColumnUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grex")
public class ProgressController {

    private final ProgressService progressService;
    private static final Logger logger = LoggerFactory.getLogger(ProgressController.class);

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/progress")
    public ResponseEntity<GenericMessage> getAllGroupProgressByStageName(@AuthenticationPrincipal @NotNull final User currentUser) {

        final String stageName = currentUser.getStageName().trim();

        logger.info("entered getAllGroupProgressByStageName:"+stageName);

        Progress progress = progressService.findAllGroupProgressByStageName(stageName);

        logger.info("data:"+progress.toString());


        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,progress), HttpStatus.OK);
    }

   @GetMapping("/progress/{gId}")
    public ResponseEntity<GenericMessage> getSpecificGroupByStageName(@AuthenticationPrincipal @NotNull final User currentUser, @PathVariable @NotNull @NotBlank final String gId) {

       logger.info("entered getSpecificGroupByStageName");

       final String stageName = currentUser.getStageName().trim();
       final String groupId = gId.trim();

       byte current_status = ProgressColumnUtil.getProgressValueByColumn(progressService.findAllGroupProgressByStageName(stageName),groupId);

       return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, new ProgressDto(groupId,current_status)), HttpStatus.OK);
   }

    @PostMapping("/progress/group/{gId}/update")
    public void updateGroupProgressStatus(@AuthenticationPrincipal @NotNull final User currentUser, @PathVariable @NotNull @NotBlank final String gId) {

        logger.info("entered updateGroupProgressStatus");

        final String stageName = currentUser.getStageName().trim();
        final String groupId = gId.trim();

        progressService.updateGroupStatus(stageName,groupId,progressService.findAllGroupProgressByStageName(stageName));

    }

}