package com.apina.api.config;

import com.apina.api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private static final Logger Logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${security.jwt.uri}")
    private String uri;

    @Value("${security.jwt.header}")
    private String authorizationHeader;

    @Value("${security.jwt.prefix}")
    private String prefix;

    public JwtAuthenticationFilter(@Lazy JwtService jwtService, @Lazy UserDetailsService userDetailsService, @Lazy HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(authorizationHeader);
        final String corsHeader = request.getHeader("Access-Control-Request-Method");
        Logger.info("Request: {}, Method {}, AuthHeader: {}", request.getRequestURI(), request.getMethod(), authHeader);
        if (corsHeader != null && "OPTIONS".equals(request.getMethod())) {
            Logger.info("CORS request detected for options. Setting status to 200.");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (authHeader == null || !authHeader.startsWith(prefix + " ")) {
            if (request.getMethod().equals("GET") || request.getRequestURI().contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            }
            handlerExceptionResolver.resolveException(request, response, null, new Exception("Missing or invalid Authorization header"));
            return;
        }
        try {
            final String jwt = authHeader.substring(7);
            final String userName = this.jwtService.extractUsername(jwt);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userName != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                if (this.jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}

