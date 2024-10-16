package com.grex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailOtpDto {

    @NotNull(message = "email can't be null.")
    @Size(min=8,max=40,message = "email must be min 8 and max 40 characters.")
    @Email(message = "email must be valid.")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "email must be valid.")
    private String email;

    @NotNull(message = "otp can't be null.")
    @Size(min=8,max=8,message = "otp must be exact 8 characters.")
   // @Pattern(regexp = "^(?=.*[A-Z])[A-Za-z0-9_@]+$", message = "password characters can only be underscore (_),@, aA-zZ & 0-9.")
    private String otp;

}
