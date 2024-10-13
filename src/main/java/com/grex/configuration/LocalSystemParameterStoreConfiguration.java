package com.grex.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;


@Configuration
@Profile("dev")
public class LocalSystemParameterStoreConfiguration {

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


    @Bean
    public DataSource dataSource() {

        // Configure HikariCP DataSource for connection pooling
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrlParamName);
        dataSource.setUsername(dbUserParamName);
        dataSource.setPassword(dbPasswordParamName);
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
        awsSystemParameterStore.setGoogleRecaptchaSecretKey(googleReCaptchaSecretKey);
        awsSystemParameterStore.setGoogleRecaptchaSecretKey(googleReCaptchaSecretUrl);

        //JWT
        awsSystemParameterStore.setSecretKey(jwtKeyParamName);
        awsSystemParameterStore.setJwtExpiration(Long.parseLong(jwtExpiryParamName));

        return awsSystemParameterStore;
    }
}

