package com.grex.controller;

import com.grex.dto.GenericMessage;
import com.grex.model.User;
import com.grex.service.OtpService;
import com.grex.service.ReCaptchaService;
import com.grex.util.OtpGenerator;
import com.grex.dto.*;
import com.grex.service.UserAuthenticationService;
import com.grex.service.email.EmailService;
import com.grex.security.JwtTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/grex/auth")
public class UserAuthenticationController {


    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationService userAuthenticationService;

    private final OtpService otpService;

    private final EmailService emailService;

    private final ReCaptchaService reCaptchaService;

    @Value("${aws.ses.from}")
    private String from;

    @Value("${aws.ses.subject}")
    private String subject;

    @Value("${aws.ses.body}")
    private String body;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired
    public UserAuthenticationController(JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, UserAuthenticationService userAuthenticationService, OtpService otpService, EmailService emailService, ReCaptchaService reCaptchaService) {
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationService = userAuthenticationService;
        this.otpService = otpService;
        this.emailService = emailService;
        this.reCaptchaService = reCaptchaService;
    }

    @PostMapping("/signup")
    public ResponseEntity<GenericMessage> signUp(@RequestBody @NotNull @Valid final SignUpUserDto signUpUserDto) {

        logger.info("Signup request received for email: {}", signUpUserDto.getEmail());

        String stageName = signUpUserDto.getUserName().trim();
        String email = signUpUserDto.getEmail().trim();
        String password = passwordEncoder.encode(signUpUserDto.getPassword().trim());

        boolean isGoogleReCaptchaTokenValid = reCaptchaService.verifyGoogleRecaptcha(signUpUserDto.getRecaptchaToken());

        logger.info("is ReCaptcha Token valid: {}", isGoogleReCaptchaTokenValid);

        if (!isGoogleReCaptchaTokenValid) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "Invalid ReCaptcha Token."), HttpStatus.BAD_REQUEST);
        }

        if (userAuthenticationService.checkEmailOrStageNameAlreadyExists(email, stageName)) {
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "Email or username already exists."), HttpStatus.BAD_REQUEST);
        }

        if (otpService.isOtpPresentByEmail(email)) {
            logger.error("OTP already sent on email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "OTP already sent on email."), HttpStatus.BAD_REQUEST);
        }

        String otp = OtpGenerator.generateOtp();
        otpService.saveOtp(stageName, email, password, otp);

        try {
            emailService.sendEmail(from, email, subject, body + otp);
            logger.info("OTP email sent to: {}", email);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to: {}. Error: {}", email, e.getMessage());
            return new ResponseEntity<>(new GenericMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP email. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, "Signup successful. Please verify with OTP."), HttpStatus.OK);
    }

    @PostMapping("/otp")
    public ResponseEntity<GenericMessage> validateOtp(@RequestBody @NotNull @Valid final EmailOtpDto emailOtpDto) {

        logger.info("OTP validation request for email: {}", emailOtpDto.getEmail());

        String email = emailOtpDto.getEmail().trim();
        String enteredOtp = emailOtpDto.getOtp().trim();

        if (!otpService.isOtpPresentByEmail(email)) {
            logger.error("OTP not found for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.BAD_REQUEST, "OTP not found."), HttpStatus.BAD_REQUEST);
        }

        SignUpUserOtpDto otpDto = otpService.getSignUpOtp(email);

        if (!otpDto.getOtp().equalsIgnoreCase(enteredOtp)) {
            logger.error("Invalid OTP for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.UNAUTHORIZED, "Invalid OTP."), HttpStatus.UNAUTHORIZED);
        }

        userAuthenticationService.signUpAfterOtpSuccessfulValidation(otpDto.getEmail(), otpDto.getStageName(), otpDto.getPassword());
        logger.info("User signup completed for email: {}", email);

        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, "OTP validated and signup completed."), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericMessage> authenticate(@RequestBody @NotNull @Valid final LoginUserDto loginUserDto) {

        logger.info("Login request received for email: {}", loginUserDto.getEmail());

        String email = loginUserDto.getEmail().trim();
        String password = loginUserDto.getPassword().trim();

        User user = userAuthenticationService.login(email, password);

        if (user == null) {
            logger.error("Invalid login attempt for email: {}", email);
            return new ResponseEntity<>(new GenericMessage(HttpStatus.UNAUTHORIZED, "Invalid credentials."), HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = jwtTokenService.generateToken(user);

        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken(jwtToken);
        jwtDto.setExpiresIn(jwtTokenService.getExpirationTime());

        logger.info("Login successful for email: {}", email);
        return new ResponseEntity<>(new GenericMessage(HttpStatus.OK, jwtDto), HttpStatus.OK);
    }
}
