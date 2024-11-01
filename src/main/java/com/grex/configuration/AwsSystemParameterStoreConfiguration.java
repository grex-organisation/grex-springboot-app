package com.grex.configuration;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@Profile("live")
public class AwsSystemParameterStoreConfiguration {

    private SsmClient ssmClient;

    @Value("${aws.ses.region}")
    private String region;

    @Value("${aws.ssm.db.url}")
    private String dbUrlParamName;

    @Value("${aws.ssm.db.username}")
    private String dbUserParamName;

    @Value("${aws.ssm.db.password}")
    private String dbPasswordParamName;

    @Value("${aws.ssm.jwt.secret.key}")
    private String jwtKeyParamName;

    @Value("${aws.ssm.jwt.secret.expiry}")
    private String jwtExpiryParamName;

    @Value("${google.recaptcha.secret.key}")
    private String googleReCaptchaSecretKey;

    @Value("${google.recaptcha.secret.url}")
    private String googleReCaptchaSecretUrl;

    @Value("${redis.host}")
    private String redisHost;

    private static final Logger logger = LoggerFactory.getLogger(AwsSystemParameterStoreConfiguration.class);


    // setup client in constructor
    public AwsSystemParameterStoreConfiguration() {}

    @PostConstruct
    private void initializeSsmClient() {
        this.ssmClient = SsmClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }



    // make a call using client to get parameter value from SSM
    private String getParameterValue(String parameterName, boolean isSecureString) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(isSecureString) // Decrypt for SecureString
                .build();

        GetParameterResponse response = ssmClient.getParameter(request);
        return response.parameter().value();
    }

    @Bean
    public DataSource dataSource() {
        // Fetch values from AWS Parameter Store
        String dbUrl = getParameterValue(dbUrlParamName, false);
        String dbUsername = getParameterValue(dbUserParamName, false);
        String dbPassword = getParameterValue(dbPasswordParamName, false);

        // Configure HikariCP DataSource for connection pooling
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AwsSystemParameterStore awsSystemParameterStore() {

        AwsSystemParameterStore awsSystemParameterStore = new AwsSystemParameterStore();

        //google recaptcha
        awsSystemParameterStore.setGoogleRecaptchaSecretKey(getParameterValue(googleReCaptchaSecretKey, false));
        awsSystemParameterStore.setGoogleRecaptchaSecretUrl(getParameterValue(googleReCaptchaSecretUrl, false));

        //JWT
        awsSystemParameterStore.setSecretKey(getParameterValue(jwtKeyParamName, false));
        awsSystemParameterStore.setJwtExpiration(Long.parseLong(getParameterValue(jwtExpiryParamName, false)));


        return awsSystemParameterStore;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(getParameterValue(redisHost, false),6379);
        return new LettuceConnectionFactory(configuration);
    }


    @Bean
    public RedisCacheManager cacheManager() {

        RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfig)
                .withCacheConfiguration("rankCache", myDefaultCacheConfig(Duration.ofMinutes(15)))
                .withCacheConfiguration("progressCache", myDefaultCacheConfig(Duration.ofMinutes(15)))
                .build();
    }

    public RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(duration)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Use StringRedisSerializer for keys and GenericJackson2JsonRedisSerializer for values
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}

