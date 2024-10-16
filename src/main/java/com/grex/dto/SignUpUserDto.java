package com.grex.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpUserDto {

    @NotNull(message = "email can't be null.")
    @Size(min=8,max=40,message = "email must be min 8 and max 40 characters.")
    @Email(message = "email must be valid.")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "email must be valid.")
    private String email;

    @NotNull(message = "username can't be null.")
    @Size(min=8,max=20,message = "username must be min 8 and max 20 characters.")
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "username characters can only be underscore (_), aA-zZ & 0-9.")
    private String userName;

    @NotNull(message = "password can't be null.")
    @Size(min=8,max=20,message = "password must be min 8 and max 20 characters.")
    @Pattern(regexp = "^(?=.*[A-Z])[A-Za-z0-9_@]+$", message = "password characters can only be underscore (_),@, aA-zZ & 0-9.")
    private String password;

    @NotNull(message = "reCaptcha token can't be null.")
    @Size(min=1,message = "reCaptcha must have characters.")
    private String recaptchaToken;
}
