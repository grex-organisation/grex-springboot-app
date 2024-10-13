package com.grex.user.controller;

import com.grex.common.messages.GenericMessage;
import com.grex.common.model.*;
import com.grex.common.util.OtpGenerator;
import com.grex.email.service.EmailService;
import com.grex.security.JwtTokenService;
import com.grex.user.model.GrexUser;
import com.grex.user.service.GrexUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/grex/auth")
public class GrexUserController {


    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final GrexUserService grexUserService;
    private final EmailService emailService;

    @Value("${aws.ses.from}")
    private String from;

    @Value("${aws.ses.subject}")
    private String subject;

    @Value("${aws.ses.body}")
    private String body;

    public GrexUserController(JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder,
                              GrexUserService grexUserService, EmailService emailService) {
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.grexUserService = grexUserService;
        this.emailService = emailService;
    }

    private static final Logger logger = LoggerFactory.getLogger(GrexUserController.class);

    @PostMapping("/signup")
    public ResponseEntity<GenericMessage> signUp(@RequestBody @NotNull @Valid final RegisterUserDto registerUserDto) {

        logger.info("Signup request received for email: {}", registerUserDto.getEmail());

        String stageName = registerUserDto.getUserName().trim();
        String email = registerUserDto.getEmail().trim();
        String password = passwordEncoder.encode(registerUserDto.getPassword().trim());

        boolean isGoogleReCaptchaTokenValid = grexUserService.verifyGoogleRecaptcha(registerUserDto.getRecaptchaToken());

        logger.info("is ReCaptcha Token valid: {}", isGoogleReCaptchaTokenValid);

        if (!isGoogleReCaptchaTokenValid) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "Invalid ReCaptcha Token."), HttpStatus.BAD_REQUEST);
        }


        // Check if email or stage name already exists
        if (grexUserService.checkEmailOrStageNameAlreadyExists(email, stageName)) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "Email or username already exists."), HttpStatus.BAD_REQUEST);
        }

        // Check if OTP is already sent for the email
        if (grexUserService.isOtpPresentByEmail(email)) {
            logger.error("OTP already sent on email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "OTP already sent on email."), HttpStatus.BAD_REQUEST);
        }

        // Generate OTP and store user details
        String otp = OtpGenerator.generateOtp();
        grexUserService.storeDetailsWithOtp(stageName, email, password, otp);

        try {
            // Attempt to send email
            emailService.sendEmail(from, email, subject, body + otp);
            logger.info("OTP email sent to: {}", email);
        } catch (Exception e) {
            // Log the email sending failure
            logger.error("Failed to send OTP email to: {}. Error: {}", email, e.getMessage());

            // Return an error response
            return new ResponseEntity<>(new GenericMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP email. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return successful signup response
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, "Signup successful. Please verify with OTP."), HttpStatus.OK);
    }


    @PostMapping("/otp")
    public ResponseEntity<GenericMessage> validateOtp(@RequestBody @NotNull @Valid final EmailOtpDto emailOtpDto) {
        logger.info("OTP validation request for email: {}", emailOtpDto.getEmail());

        String email = emailOtpDto.getEmail().trim();
        String enteredOtp = emailOtpDto.getOtp().trim();

        if (!grexUserService.isOtpPresentByEmail(email)) {
            logger.error("OTP not found for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "OTP not found."), HttpStatus.BAD_REQUEST);
        }

        RegisterUserOtpDto otpDto = grexUserService.checkStoredOtp(email);

        if (!otpDto.getOtp().equalsIgnoreCase(enteredOtp)) {
            logger.error("Invalid OTP for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.UNAUTHORIZED, "Invalid OTP."), HttpStatus.UNAUTHORIZED);
        }

        grexUserService.signUpAfterOtpValidation(otpDto.getEmail(), otpDto.getStageName(), otpDto.getPassword());
        logger.info("User signup completed for email: {}", email);

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, "OTP validated and signup completed."), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericMessage> authenticate(@RequestBody @NotNull @Valid final LoginUserDto loginUserDto) {
        logger.info("Login request received for email: {}", loginUserDto.getEmail());

        String email = loginUserDto.getEmail().trim();
        String password = loginUserDto.getPassword().trim();

        GrexUser grexUser = grexUserService.login(email, password);
        if (grexUser == null) {
            logger.error("Invalid login attempt for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.UNAUTHORIZED, "Invalid credentials."), HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = jwtTokenService.generateToken(grexUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtTokenService.getExpirationTime());

        logger.info("Login successful for email: {}", email);
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, loginResponse), HttpStatus.OK);
    }

}
