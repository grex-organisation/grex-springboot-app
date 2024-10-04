package com.grex.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JWT {

    private String secretKey;

    private long jwtExpiration;

}
