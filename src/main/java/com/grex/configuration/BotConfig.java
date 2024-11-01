package com.grex.configuration;

import com.grex.dto.BotDto;
import com.grex.model.Group;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "bots")
public class BotConfig {

    private List<BotDto> bots;

}

