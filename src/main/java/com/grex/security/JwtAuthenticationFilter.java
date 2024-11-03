package com.grex.security;

import com.grex.configuration.AwsSystemParameterStore;
import com.grex.service.ProgressService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;


//https://medium.com/geekculture/spring-security-authentication-process-authentication-flow-behind-the-scenes-d56da63f04fa
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtTokenService jwtService;

    private final UserDetailsService userDetailsService;

    private final AwsSystemParameterStore awsSystemParameterStore;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Autowired
    public JwtAuthenticationFilter(JwtTokenService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver, AwsSystemParameterStore awsSystemParameterStore) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.awsSystemParameterStore = awsSystemParameterStore;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Check for the custom CDN authentication header only for CDN server Bunny.net
        // Custom logic for requests to /api/grex/ranking/** using header
        // Bypass authentication for /api/grex/auth/**, handled in SecurityFilterChain configuration
        final String cdnAuthHeader = request.getHeader(awsSystemParameterStore.getSecretCDNHeader());
        final String requestURI = request.getRequestURI();

        logger.info("request URI:"+requestURI);
        logger.info("CDN header is: "+cdnAuthHeader);
        logger.info("CDN secret is:"+awsSystemParameterStore.getSecretCDNKey());

        if (requestURI.startsWith("/api/grex/cdn/") && cdnAuthHeader != null && cdnAuthHeader.equals(awsSystemParameterStore.getSecretCDNKey())) {
            // Assign a temporary role for CDN access
            logger.info("request for cdn content only");
            UsernamePasswordAuthenticationToken cdnAuth = new UsernamePasswordAuthenticationToken("cdnUser", null, List.of(new SimpleGrantedAuthority("ROLE_CDN")));
            SecurityContextHolder.getContext().setAuthentication(cdnAuth);
            filterChain.doFilter(request, response);  // Skip further checks
            return;
        }

        logger.info("request for non-cdn");
        // Do normal if not from CDN
        final String authHeader = request.getHeader("Authorization"); // fetching the Bearer token from request header.

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // if header is not present or header is missng Bearer then do...
            filterChain.doFilter(request,response);
            return;
        }

        logger.info("authenticate based on JWT");
        try {
            final String jwt = authHeader.substring(7); // Removing "Bearer " from JWT token
            final String userEmail = jwtService.extractUsername(jwt); //fetching email from JWT token.

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //get the authentication object from current SpringSecurityContext.

            if (userEmail != null && authentication == null) { //if email is present but authentication object is null.

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);  //load user from DB based on email.

                if (jwtService.isTokenValid(jwt, userDetails)) { // if userdetails loaded from DB matches the JWT token details from header.
                    //if true, jwt details matches with user details from DB
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // add additional details about request like remote id and session id.
                    SecurityContextHolder.getContext().setAuthentication(authToken); // set authentication token to current security context.
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
