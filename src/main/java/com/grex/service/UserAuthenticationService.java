package com.grex.service;


import com.grex.persistence.OtpRepository;
import com.grex.persistence.ProgressRepository;
import com.grex.model.User;
import com.grex.persistence.ScoreRepository;
import com.grex.persistence.UserAuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthenticationService {


    private final UserAuthenticationRepository userRepository;

    private final OtpRepository otpRepository;

    private final ProgressRepository progressRepository;

    private final ScoreRepository scoreRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserAuthenticationService(UserAuthenticationRepository userRepository, ProgressRepository progressRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, OtpRepository otpRepository, ScoreRepository scoreRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.otpRepository = otpRepository;
        this.scoreRepository = scoreRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

    public boolean checkEmailOrStageNameAlreadyExists(final String email, final String stageName) {
        logger.info("entered checkEmailOrStageNameAlreadyExists method ");
        return userRepository.checkEmailOrStageNameAlreadyExists(email, stageName);
    }

    @Transactional
    public void signUpAfterOtpSuccessfulValidation(final String email, final String stageName, final String password) {

        logger.info("entered signUpAfterOtpValidation method");

        userRepository.addNewUser(email, stageName, password, "USER", true, false);
        otpRepository.deleteOtpRecord(email);
        progressRepository.makeFirstEntryInProgress(stageName);
        scoreRepository.makeFirstEntryInScore(stageName,"US");

    }

    public User login(final String email, final String password) {
        logger.info("entered login method ");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return userRepository.findByEmail(email);
    }

}
