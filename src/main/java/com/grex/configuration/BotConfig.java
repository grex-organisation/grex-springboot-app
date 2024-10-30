package com.grex.configuration;

import com.grex.model.Group;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "groups")
public class WordConfig {

    private List<Group> groups;

}

