package com.grex.word.config;

import com.grex.word.model.Group;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "groups")
public class WordConfig {
    private List<Group> groups;
}

