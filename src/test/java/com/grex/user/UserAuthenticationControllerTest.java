package com.grex.user;

import com.grex.controller.UserAuthenticationController;
import com.grex.dto.EmailOtpDto;
import com.grex.dto.LoginUserDto;
import com.grex.dto.SignUpUserDto;
import com.grex.service.OtpService;
import com.grex.service.email.EmailService;
import com.grex.security.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationControllerTest {

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    //@Mock
    //private UserAuthenticationService grexUserService;

    @Mock
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    private SignUpUserDto signUpUserDto;
    private EmailOtpDto emailOtpDto;
    private LoginUserDto loginUserDto;

    @BeforeEach
    void setUp() {

        // Create dummy objects for the test cases
        signUpUserDto = new SignUpUserDto();
        signUpUserDto.setEmail("test@example.com");
        signUpUserDto.setPassword("Qwerty12345");
        signUpUserDto.setRecaptchaToken("dummyRecaptchaToken");
        signUpUserDto.setUserName("testUser");

        emailOtpDto = new EmailOtpDto();
        emailOtpDto.setEmail("test@example.com");
        emailOtpDto.setOtp("AxtrWq1s");

        loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("password");
    }



//    @Test
//    void testSignUp_Success() {
//
//        // Mock behavior
//        //when(otpService.verifyGoogleRecaptcha(anyString())).thenReturn(true);
//       // when(otpService.checkEmailOrStageNameAlreadyExists(anyString(), anyString())).thenReturn(false);
//        when(otpService.isOtpPresentByEmail(anyString())).thenReturn(false);
//        //doNothing().when(emailService.sendEmail(anyString(), anyString(), anyString(), anyString());
//
//        // Test
//        ResponseEntity<GenericMessage> response = userAuthenticationController.signUp(signUpUserDto);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Signup successful. Please verify with OTP.", response.getBody().getData());
//    }

//  @Test
//    void testSignUp_InvalidRecaptcha() {
//       // when(otpService.verifyGoogleRecaptcha(anyString())).thenReturn(false);
//
//        ResponseEntity<GenericMessage> response = userAuthenticationController.signUp(signUpUserDto);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Invalid ReCaptcha Token.", response.getBody().getData());
//    }
 /*
    @Test
    void testSignUp_EmailOrStageNameExists() {
        when(grexUserService.verifyGoogleRecaptcha(anyString())).thenReturn(true);
        when(grexUserService.checkEmailOrStageNameAlreadyExists(anyString(), anyString())).thenReturn(true);

        ResponseEntity<GenericMessage> response = userAuthenticationController.signUp(signUpUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email or username already exists.", response.getBody().getMessage());
    }

    @Test
    void testSignUp_OtpAlreadySent() {
        when(grexUserService.verifyGoogleRecaptcha(anyString())).thenReturn(true);
        when(grexUserService.checkEmailOrStageNameAlreadyExists(anyString(), anyString())).thenReturn(false);
        when(grexUserService.isOtpPresentByEmail(anyString())).thenReturn(true);

        ResponseEntity<GenericMessage> response = userAuthenticationController.signUp(signUpUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("OTP already sent on email.", response.getBody().getMessage());
    }

    @Test
    void testSignUp_EmailSendFailure() {
        when(grexUserService.verifyGoogleRecaptcha(anyString())).thenReturn(true);
        when(grexUserService.checkEmailOrStageNameAlreadyExists(anyString(), anyString())).thenReturn(false);
        when(grexUserService.isOtpPresentByEmail(anyString())).thenReturn(false);
        doThrow(new RuntimeException("Email failed")).when(emailService).sendEmail(anyString(), anyString(), anyString(), anyString());

        ResponseEntity<GenericMessage> response = userAuthenticationController.signUp(signUpUserDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to send OTP email. Please try again later.", response.getBody().getMessage());
    }

    @Test
    void testValidateOtp_Success() {
        when(grexUserService.isOtpPresentByEmail(anyString())).thenReturn(true);
        when(grexUserService.checkStoredOtp(anyString())).thenReturn(new SignUpUserOtpDto("test@example.com", "testUser", "password", "123456"));

        ResponseEntity<GenericMessage> response = userAuthenticationController.validateOtp(emailOtpDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP validated and signup completed.", response.getBody().getMessage());
    }

    @Test
    void testValidateOtp_OtpNotFound() {
        when(grexUserService.isOtpPresentByEmail(anyString())).thenReturn(false);

        ResponseEntity<GenericMessage> response = userAuthenticationController.validateOtp(emailOtpDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("OTP not found.", response.getBody().getMessage());
    }

    @Test
    void testValidateOtp_InvalidOtp() {
        when(grexUserService.isOtpPresentByEmail(anyString())).thenReturn(true);
        when(grexUserService.checkStoredOtp(anyString())).thenReturn(new SignUpUserOtpDto("test@example.com", "testUser", "password", "wrongOtp"));

        ResponseEntity<GenericMessage> response = userAuthenticationController.validateOtp(emailOtpDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid OTP.", response.getBody().getMessage());
    }

    @Test
    void testAuthenticate_Success() {
        User mockUser = new User("test@example.com", "testUser", "password", "ROLE_USER", true, false);
        when(grexUserService.login(anyString(), anyString())).thenReturn(mockUser);
        when(jwtTokenService.generateToken(any(User.class))).thenReturn("dummyJwtToken");

        ResponseEntity<GenericMessage> response = userAuthenticationController.authenticate(loginUserDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getMessage());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        when(grexUserService.login(anyString(), anyString())).thenReturn(null);

        ResponseEntity<GenericMessage> response = userAuthenticationController.authenticate(loginUserDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials.", response.getBody().getMessage());
    }*/
}
