package com.grex.dto;

import lombok.Data;

@Data
public class JwtDto {

    private String token;

    private long expiresIn;

}