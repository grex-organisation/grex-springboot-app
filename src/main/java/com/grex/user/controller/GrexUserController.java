package com.grex.user.controller;


import com.grex.common.exception.types.UserEmailAlreadyExistsException;
import com.grex.common.messages.GenericMessage;
import com.grex.common.model.*;
import com.grex.common.util.OtpGenerator;
import com.grex.email.service.EmailService;
import com.grex.progress.service.ProgressService;
import com.grex.security.JwtTokenService;
import com.grex.user.model.GrexUser;
import com.grex.user.service.GrexUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/grex/auth")
@RestController
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


    @Autowired
    public GrexUserController(JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, GrexUserService grexUserService, ProgressService progressService, EmailService emailService) {
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.grexUserService = grexUserService;
        this.emailService = emailService;
    }

    private static final Logger logger = LoggerFactory.getLogger(GrexUserController.class);

    @PostMapping("/signup")
    public void signUp(@RequestBody @NotNull @Valid final RegisterUserDto registerUserDto) {

        logger.info("Entered signup method");

        final String stageName = registerUserDto.getUserName().trim();
        final String email = registerUserDto.getEmail().trim();
        final String password = passwordEncoder.encode(registerUserDto.getPassword().trim());

        logger.info("entered signup email:" + email + ",stage name:" + stageName + ",password: ********");

        boolean isPresent = grexUserService.checkEmailOrStageNameAlreadyExists(email, stageName);

        logger.info("email or username already present:" + !isPresent);

        if (!isPresent) {

            String otp = OtpGenerator.generateOtp();
            logger.info("otp generated:********");

            boolean isOtpPresent = grexUserService.storeDetailsWithOtp(stageName, email, password, otp);
            logger.info("is record present in otp table:" + isOtpPresent);

            if (isOtpPresent) {
                emailService.sendEmail(from, email, subject, body + otp);
                logger.info("signup email sent");
            }
        } else {
            logger.error("Email or stage name already exist");
            throw new UserEmailAlreadyExistsException("Email or stage name already exist");
        }

        logger.info("Exited signup method");

    }

    @PostMapping("/otp")
    public void validateOtp(@RequestBody @NotNull @Valid final EmailOtpDto emailOtpDto) {

        logger.info("entered validateOtp");

        final String email = emailOtpDto.getEmail().trim();
        final String entered_otp = emailOtpDto.getOtp().trim();

        logger.info("entered signup email:" + email + ",otp: ********");

        boolean isOtpPresent = grexUserService.isOtpPresentByEmail(email);

        logger.info("isOtpPresent"+isOtpPresent);

        if (isOtpPresent) {

            RegisterUserOtpDto otpDto = grexUserService.checkStoredOtp(email);

            if (otpDto.getOtp().equalsIgnoreCase(entered_otp)) {
                logger.info("otp matched");
                grexUserService.signUpAfterOtpValidation(otpDto.getEmail(), otpDto.getStageName(), otpDto.getPassword());
                logger.info("signUpAfterOtpValidation called");

            } else {
               logger.error("otp mismatch");
            }
        } else {
             logger.error("otp no present");
        }

        logger.info("exited validateOtp");

    }

    @PostMapping("/login")
    public ResponseEntity<GenericMessage> authenticate(@RequestBody @NotNull @Valid LoginUserDto loginUserDto) {

        logger.info("entered /login authenticate method");

        final String email = loginUserDto.getEmail().trim();
        final String password = loginUserDto.getPassword().trim();

        logger.info("entered login email:" + email + ",password: ********");

        GrexUser grexUser = grexUserService.login(email, password);

        logger.info("authenticated user is present:"+(grexUser!=null));

        String jwtToken = jwtTokenService.generateToken(grexUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtTokenService.getExpirationTime());

        GenericMessage response = new GenericMessage(HttpStatus.OK, loginResponse);

        logger.info("exiting /login authenticate method");

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

}