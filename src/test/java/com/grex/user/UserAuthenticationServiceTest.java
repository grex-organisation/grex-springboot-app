package com.grex.user;

import com.grex.dto.SignUpUserDto;
import com.grex.dto.SignUpUserOtpDto;
import com.grex.persistence.ProgressRepository;
import com.grex.persistence.UserAuthenticationRepository;
import com.grex.service.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserAuthenticationRepository userRepository;

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    private SignUpUserDto signUpUserDto;
    private SignUpUserOtpDto signUpUserOtpDtoDto;

    @BeforeEach
    void setUp(){

        signUpUserDto = new SignUpUserDto();
        signUpUserDto.setEmail("test@example.com");
        signUpUserDto.setPassword("Qwerty12345");
        signUpUserDto.setRecaptchaToken("dummyRecaptchaToken");
        signUpUserDto.setUserName("testUser");

        signUpUserOtpDtoDto = new SignUpUserOtpDto();
        signUpUserOtpDtoDto.setEmail("test@example.com");
        signUpUserOtpDtoDto.setStageName("testUser");
        signUpUserOtpDtoDto.setPassword("Qwert12345");
        signUpUserOtpDtoDto.setOtp("ABcd1234");

    }

/*    @Test
    public void checkOTPByEmail_present(){

        when(userRepository.isOtpPresentByEmail(anyString())).thenReturn(true);
        boolean isOtpPresent = userAuthenticationService.isOtpPresentByEmail(signUpUserDto.getEmail());
        assertTrue(isOtpPresent);
    }

    @Test
    public void checkOTPByEmail_not_present(){

        when(userRepository.isOtpPresentByEmail(anyString())).thenReturn(false);
        boolean isOtpPresent = userAuthenticationService.isOtpPresentByEmail(signUpUserDto.getEmail());
        assertFalse(isOtpPresent);
    }

    @Test
    public void checkStoredotp_sucess() {

        when(userRepository.checkStoredOtp(anyString())).thenReturn(signUpUserOtpDtoDto);
        SignUpUserOtpDto response = userRepository.checkStoredOtp(signUpUserOtpDtoDto.getEmail());
        assertEquals("ABcd1234",response.getOtp());

    }*/


}
