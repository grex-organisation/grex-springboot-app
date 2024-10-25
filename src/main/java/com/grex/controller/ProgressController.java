package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.dto.ProgressDto;
import com.grex.model.Progress;
import com.grex.model.User;
import com.grex.service.CacheService;
import com.grex.service.ProgressService;
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
    private final CacheService cacheService;

    private static final Logger logger = LoggerFactory.getLogger(ProgressController.class);

    @Autowired
    public ProgressController(ProgressService progressService, CacheService cacheService) {
        this.progressService = progressService;
        this.cacheService = cacheService;
    }

    @GetMapping("/progress")
    public ResponseEntity<GenericMessage> getAllGroupProgressByStageName(@AuthenticationPrincipal @NotNull final User currentUser) {

        logger.info("entered getAllGroupProgressByStageName");

        final String stageName = currentUser.getStageName().trim();

        Progress progress = null;

        //if not present in cache memory than load from main database and also keep in cache.
        if(!cacheService.getProgressMap().containsKey(stageName)){
            logger.info("loading {} progress from db to cache memory",stageName);
            progress = progressService.findAllGroupProgressByStageName(stageName);
            cacheService.getProgressMap().put(stageName,progress);
        }

        progress = cacheService.getProgressMap().get(stageName);
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK,progress), HttpStatus.OK);
    }

   @GetMapping("/progress/{gId}")
    public ResponseEntity<GenericMessage> getSpecificGroupByStageName(@AuthenticationPrincipal @NotNull final User currentUser, @PathVariable @NotNull @NotBlank final String gId) {

       logger.info("entered getSpecificGroupByStageName");

       final String stageName = currentUser.getStageName().trim();
        final String groupId = gId.trim();

       // check groupId is present in cached memory - GroupMap
       if (!cacheService.getCachedGroupMap().containsKey(groupId)) {
           return new ResponseEntity<>(new GenericMessage(HttpStatus.NOT_FOUND, "Group not found"), HttpStatus.NOT_FOUND);
       }

       //if not present in cache memory than load from main database and also keep in cache.
       if(!cacheService.getProgressMap().containsKey(stageName)){
           logger.info("loading {} progress from db to cache memory",stageName);
           Progress progress = progressService.findAllGroupProgressByStageName(stageName);
           cacheService.getProgressMap().put(stageName,progress);
       }

       //now load again from cache
       byte current_status = cacheService.getCachedProgressByGroup(stageName,groupId);
       return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, new ProgressDto(groupId,current_status)), HttpStatus.OK);
   }

    @PostMapping("/progress/group/{gId}/update")
    public void updateGroupProgressStatus(@AuthenticationPrincipal @NotNull final User currentUser, @PathVariable @NotNull @NotBlank final String gId) {

        logger.info("entered updateGroupProgressStatus");

        final String stageName = currentUser.getStageName().trim();
        final String groupId = gId.trim();

        //check valid groupId
        if (!cacheService.getCachedGroupMap().containsKey(groupId)) {
            logger.info("invalid group id");
            return;
        }

        //if not present in cache memory than load from main database and also keep in cache.
        if(!cacheService.getProgressMap().containsKey(stageName)){
            logger.info("loading {} progress from db to cache memory",stageName);
            Progress progress = progressService.findAllGroupProgressByStageName(stageName);
            cacheService.getProgressMap().put(stageName,progress);
        }

        cacheService.setCachedProgressByGroup(stageName,groupId);

    }

}