package com.grex.service;

import com.grex.model.Group;
import com.grex.model.Progress;
import com.grex.persistence.ProgressRepository;
import com.grex.util.ProgressColumnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ProgressService(ProgressRepository progressRepository, RedisTemplate<String, Object> redisTemplate) {
        this.progressRepository = progressRepository;
        this.redisTemplate = redisTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProgressService.class);

    @Cacheable(value="progressCache",key = "#stageName",unless = "#result == null")
    public Progress findAllGroupProgressByStageName(final String stageName) {
        logger.info("@Cacheable findAllGroupProgressByStageName started");
        return progressRepository.findGroupsProgressByStageName(stageName);

    }

    // Updating the cache data for stageName
    @CachePut(value = "progressCache", key = "#stageName")
    public Progress updateGroupStatus(final String stageName, final String groupId, final Progress exitingProgressCacheByStageName) {
        logger.info("@CachePut updateGroupStatus started");

        ProgressColumnUtil.setProgressValueByColumn(exitingProgressCacheByStageName, groupId, (byte) 1);

        // Return the updated Progress object to store it in the cache
        return exitingProgressCacheByStageName;
    }

    // Caching the action of dumping cache data to the database
    @CacheEvict(value = "progressCache", allEntries = true)
    @Scheduled(fixedRate = 100000)  // Run every 15 minutes as an example
    public void dumpCacheToDatabase() {

        logger.info("@CacheEvict dumpCacheToDatabase started");

        // Retrieve all keys matching the pattern for your cache
        Set<String> keys = redisTemplate.keys("progressCache::*");

        List<Progress> cacheProgressList = new ArrayList<>();

        if (keys != null) {
            for (String key : keys) {
                Progress cachedProgress = (Progress) redisTemplate.opsForValue().get(key);
                cacheProgressList.add(cachedProgress);

            }
        }

        // Perform bulk update
        try {
            progressRepository.batchUpdateProgress(cacheProgressList);
            logger.info("Bulk update to GREX_PROGRESS table completed");

        } catch (Exception e) {
            logger.error("Error updating database: ", e);
        }

        // Cache is evicted at the end of the method due to @CacheEvict annotation
    }

}
