package com.grex.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


//https://medium.com/geekculture/spring-security-authentication-process-authentication-flow-behind-the-scenes-d56da63f04fa
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtTokenService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {



        final String authHeader = request.getHeader("Authorization"); // fetching the Bearer token from request header.

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // if header is not present or header is missng Bearer then do...
            System.out.println("Header is null or Bearer token not present.calling directly filter chain");
            filterChain.doFilter(request,response);
            //response.setStatus(401);
            //response.setContentType("application/json");
            //response.getWriter().write("{{\"code\":401,\"status\":\"UNAUTHORIZED\",\"data\":{\"error\":\"missing jwt token \",\"trace\" : \"missing jwt token\"}}}");
            return;
        }

        // Bearer token is present then come here.
       // JWT token =  HEADER + PAYLOAD + SIGNATURE

        try {
            final String jwt = authHeader.substring(7); // Removing "Bearer " from JWT token
            final String userEmail = jwtService.extractUsername(jwt); //fetching email from JWT token.

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //get the authentication object from current SpringSecurityContext.

            if (authentication == null) {System.out.println("Authentication Object is NULL");} else {System.out.println("Authentication Object is:"+authentication.toString());}

            if (userEmail != null && authentication == null) { //if email is present but authentication object is null.

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);  //load user from DB based on email.
                System.out.println("UserDetails are: "+userDetails.toString());

                if (jwtService.isTokenValid(jwt, userDetails)) { // if userdetails loaded from DB matches the JWT token details from header.
                    //if true, jwt details matches with user details from DB
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    System.out.println("Authentication Token is:"+authToken);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // add additional details about request like remote id and session id.

                    System.out.println("Setting Authentication Token to Security Context");
                    SecurityContextHolder.getContext().setAuthentication(authToken); // set authentication token to current security context.
                }
            }

            System.out.println("Calling filter chain");
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
