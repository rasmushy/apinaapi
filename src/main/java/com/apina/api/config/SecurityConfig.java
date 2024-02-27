package com.apina.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// SecurityConfig.java
@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.roles.admin}")
    private String ADMIN;

    @Value("${app.roles.user}")
    private String USER;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigin;

    @Value("${app.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${app.cors.allowed-methods}")
    private String allowedMethods;

    private static final String API_GYM = "/api/gym/**";
    private static final String API_GYMS = "/api/gyms/**";
    private static final String API_USER = "/api/user/**";
    private static final String API_AUTH = "/api/auth/**";
    private static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    private static final String[] SWAGGER_UI = {"/", "/swagger-ui/**", "/v3/api-docs/**", SWAGGER_UI_HTML, "/webjars/**", "/swagger-resources/**"};

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_UI).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, API_GYM, API_GYMS, API_USER).permitAll()
                        .requestMatchers(HttpMethod.POST, API_AUTH).permitAll()
                        .requestMatchers(HttpMethod.POST, API_GYM).hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.PUT, API_USER).hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.POST, API_GYMS).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, API_GYMS, API_GYM).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_GYMS, API_GYM, API_USER).hasRole(ADMIN)
                        .anyRequest().fullyAuthenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(allowedOrigin)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders)
                .allowCredentials(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", SWAGGER_UI_HTML);
        registry.addRedirectViewController("/swagger", SWAGGER_UI_HTML);
        registry.addRedirectViewController("/swagger-ui", SWAGGER_UI_HTML);
        registry.addRedirectViewController("/swagger-ui/", SWAGGER_UI_HTML);
    }

}

