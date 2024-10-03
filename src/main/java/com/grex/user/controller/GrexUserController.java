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

    @PostMapping("/signup")
    public void signUp(@RequestBody @NotNull @Valid final RegisterUserDto registerUserDto) {

        final String stageName = registerUserDto.getUserName().trim();
        final String email = registerUserDto.getEmail().trim();
        final String password = passwordEncoder.encode(registerUserDto.getPassword().trim());

        //check user email or stageName already exits
        if(!grexUserService.checkEmailOrStageNameAlreadyExists(email,stageName)){

        // if username or stageName do not already exist then generate OTP and send on email to verify user.
            String otp = OtpGenerator.generateOtp();

            // if user details along with generated OTP is stored successfully in GREX_USER_OTP table.
            if(grexUserService.storeDetailsWithOtp(stageName,email,password,otp)){
                //send OTP over email
                emailService.sendEmail(from,email,subject,body + otp);
            }
        }else{
            throw new UserEmailAlreadyExistsException("Email or stage name already exist");
        }

    }

    @PostMapping("/otp")
    public void validateOtp(@RequestBody @NotNull @Valid final EmailOtpDto emailOtpDto) {

        final String email = emailOtpDto.getEmail().trim();
        final String entered_otp = emailOtpDto.getOtp().trim();

        //check if email with otp present
        boolean isOtpPresent = grexUserService.isOtpPresentByEmail(email);

        if(isOtpPresent){

            // get user details with OTP
            RegisterUserOtpDto otpDto = grexUserService.checkStoredOtp(email);

            // compare entered OTP with generated OTP
            if(otpDto.getOtp().equalsIgnoreCase(entered_otp)){
                grexUserService.signUpAfterOtpValidation(otpDto.getEmail(),otpDto.getStageName(),otpDto.getPassword());
            }else{
                //do something as otp linked with email is wrong
            }
        }else{
            // do something as email with otp no present.
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GenericMessage> authenticate(@RequestBody @NotNull @Valid LoginUserDto loginUserDto) {

        final String email = loginUserDto.getEmail().trim();
        final String password = loginUserDto.getPassword().trim();

        GrexUser grexUser = grexUserService.login(email,password);

        String jwtToken = jwtTokenService.generateToken(grexUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtTokenService.getExpirationTime());

        GenericMessage response = new GenericMessage(HttpStatus.OK,loginResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}