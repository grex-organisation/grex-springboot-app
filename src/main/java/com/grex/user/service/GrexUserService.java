package com.grex.user.service;


import com.grex.common.model.RegisterUserOtpDto;
import com.grex.progress.persistence.ProgressRepository;
import com.grex.user.model.GrexUser;
import com.grex.user.persistence.GrexUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrexUserService {

    private final GrexUserRepository userRepository;

    private final ProgressRepository progressRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public GrexUserService(GrexUserRepository userRepository, ProgressRepository progressRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
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
        logger.debug("called addNewUser");

        userRepository.deleteOtpRecord(email);
        logger.debug("called deleteOtpRecord");

        progressRepository.addSignUpProgress(stageName);
        logger.debug("called addSignUpProgress");

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


}
