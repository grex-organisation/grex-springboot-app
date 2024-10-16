package com.grex.service;

import com.grex.dto.SignUpUserOtpDto;
import com.grex.persistence.OtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    @Autowired
    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    public boolean saveOtp(final String stageName, final String email, final String password, final String otp) {

        logger.info("entered storeDetailsWithOtp method ");
        return otpRepository.saveOtp(email, stageName, password, otp);
    }

    public boolean isOtpPresentByEmail(final String email) {
        logger.info("entered isOtpPresentByEmail method ");
        return otpRepository.findOtpByEmail(email);
    }

    public SignUpUserOtpDto getSignUpOtp(final String email) {
        logger.info("entered checkStoredOtp method ");
        return otpRepository.getSignUpOtp(email);
    }

}
