package com.grex.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@Configuration
public class AwsSystemParameterStoreConfiguration {

    private final SsmClient ssmClient;

    public AwsSystemParameterStoreConfiguration() {
        this.ssmClient = SsmClient.builder()
                .region(Region.of("ap-south-1")) // Set your AWS region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public String dbUrl() {
        return getParameterValue("DB_URL", false);
    }

    @Bean
    public String dbUsername() {
        return getParameterValue("DB_USER", false);
    }

    @Bean
    public String dbPassword() {
        return getParameterValue("DB_PASSWORD", false);
    }

    @Bean
    public String dbPlatform() {
        return getParameterValue("DB_PLATFORM", false);
    }

    private String getParameterValue(String parameterName, boolean isSecureString) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(isSecureString) // Decrypt for SecureString
                .build();

        GetParameterResponse response = ssmClient.getParameter(request);
        return response.parameter().value();
    }
}

