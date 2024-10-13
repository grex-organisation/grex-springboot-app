package com.grex.configuration;

import lombok.Data;

@Data
public class AwsSystemParameterStore {

    private String googleRecaptchaSecretKey;
    private String googleRecaptchaSecretUrl;
    private String secretKey;
    private long jwtExpiration;

}
