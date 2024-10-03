package com.grex.common.model;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private long expiresIn;

}