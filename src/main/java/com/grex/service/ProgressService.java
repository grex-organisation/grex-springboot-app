package com.grex.service;

import com.grex.model.Progress;
import com.grex.persistence.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public Progress findAllGroupProgressByStageName(final String username) {
        return progressRepository.findGroupsProgressByStageName(username);
    }

    public byte findSpecificGroupProgressByStageName(final String stageName, final String groupId) {
        return  progressRepository.findSpecificGroupProgressByStageName(stageName,groupId);
    }

    public void updateGroupStatus(final String stageName, final String groupId){
       progressRepository.updateGroupStatus(stageName,groupId);
    }

}
