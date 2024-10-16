package com.grex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotNull(message = "email can't be null.")
    @Size(min=8,max=40,message = "email must be min 8 and max 40 characters.")
    @Email(message = "email must be valid.")
    private String email;

    @NotNull(message = "password can't be null.")
    @Size(min=8,max=20,message = "password must be min 8 and max 20 characters.")
    private String password;

}
