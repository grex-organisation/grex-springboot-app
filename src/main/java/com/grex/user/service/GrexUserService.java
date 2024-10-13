package com.grex.user.service;


import com.grex.common.model.RecaptchaResponse;
import com.grex.common.model.RegisterUserOtpDto;
import com.grex.configuration.AwsSystemParameterStore;
import com.grex.progress.persistence.ProgressRepository;
import com.grex.user.model.GrexUser;
import com.grex.user.persistence.GrexUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class GrexUserService {

    private final AwsSystemParameterStore awsSystemParameterStore;

    private final RestTemplate restTemplate;

    private final GrexUserRepository userRepository;

    private final ProgressRepository progressRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public GrexUserService(AwsSystemParameterStore awsSystemParameterStore, GrexUserRepository userRepository, ProgressRepository progressRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.awsSystemParameterStore = awsSystemParameterStore;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.restTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(GrexUserService.class);

    public boolean checkEmailOrStageNameAlreadyExists(final String email, final String stageName) {
        logger.info("entered checkEmailOrStageNameAlreadyExists method ");
        return userRepository.checkEmailOrStageNameAlreadyExists(email, stageName);
    }

    @Transactional
    public void signUpAfterOtpValidation(final String email, final String stageName, final String password) {

        logger.info("entered signUpAfterOtpValidation method ");

        userRepository.addNewUser(email, stageName, password, "USER", true, false);
        logger.info("called addNewUser");

        userRepository.deleteOtpRecord(email);
        logger.info("called deleteOtpRecord");

        progressRepository.addSignUpProgress(stageName);
        logger.info("called addSignUpProgress");

        logger.info("exited signUpAfterOtpValidation method ");

    }

    public boolean storeDetailsWithOtp(final String stageName, final String email, final String password, final String otp) {

        logger.info("entered storeDetailsWithOtp method ");

        return userRepository.storeDetailsWithOtp(email, stageName, password, otp);
    }

    public boolean isOtpPresentByEmail(final String email) {

        logger.info("entered isOtpPresentByEmail method ");
        return userRepository.isOtpPresentByEmail(email);
    }

    public RegisterUserOtpDto checkStoredOtp(String email) {

        logger.info("entered checkStoredOtp method ");
        return userRepository.checkStoredOtp(email);
    }

    public GrexUser login(final String email, final String password) {

        logger.info("entered login method ");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return userRepository.findByEmail(email);
    }

    public boolean verifyGoogleRecaptcha(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String params = "secret=" + awsSystemParameterStore.getGoogleRecaptchaSecretKey() + "&response=" + token;

        HttpEntity<String> request = new HttpEntity<>(params, headers);

        ResponseEntity<RecaptchaResponse> responseEntity = restTemplate.exchange(awsSystemParameterStore.getGoogleRecaptchaSecretUrl(), HttpMethod.POST, request, RecaptchaResponse.class);

        return responseEntity.getBody() != null && responseEntity.getBody().isSuccess();
    }



}
