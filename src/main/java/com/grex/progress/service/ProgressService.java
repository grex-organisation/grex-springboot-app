package com.grex.progress.service;

import com.grex.progress.model.Progress;
import com.grex.progress.persistence.ProgressRepository;
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

    public int findSpecificGroupProgressByStageName(final String stageName, final String groupId) {
        return  progressRepository.findSpecificGroupProgressByStageName(stageName,groupId);
    }

}
