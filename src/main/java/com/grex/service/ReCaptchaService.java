package com.grex.service;

import com.grex.configuration.AwsSystemParameterStore;
import com.grex.dto.RecaptchaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaService {

    private final AwsSystemParameterStore awsSystemParameterStore;

    private final RestTemplate restTemplate;

    @Autowired
    public ReCaptchaService(AwsSystemParameterStore awsSystemParameterStore, RestTemplate restTemplate) {
        this.awsSystemParameterStore = awsSystemParameterStore;
        this.restTemplate = restTemplate;
    }

    public boolean verifyGoogleRecaptcha(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String params = "secret=" + awsSystemParameterStore.getGoogleRecaptchaSecretKey() + "&response=" + token;

        HttpEntity<String> request = new HttpEntity<>(params, headers);

        ResponseEntity<RecaptchaDto> responseEntity = restTemplate.exchange(awsSystemParameterStore.getGoogleRecaptchaSecretUrl(), HttpMethod.POST, request, RecaptchaDto.class);

        return responseEntity.getBody() != null && responseEntity.getBody().isSuccess();
    }
}
