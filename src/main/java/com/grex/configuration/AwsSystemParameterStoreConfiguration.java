package com.grex.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import javax.sql.DataSource;

@Configuration
@Profile("live")
public class AwsSystemParameterStoreConfiguration {

    private final SsmClient ssmClient;

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


    // setup client in constructor
    public AwsSystemParameterStoreConfiguration() {
        this.ssmClient = SsmClient.builder()
                .region(Region.of("ap-south-1")) // Set your AWS region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    private static final Logger logger = LoggerFactory.getLogger(AwsSystemParameterStoreConfiguration.class);


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




}

