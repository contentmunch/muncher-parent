package com.contentmunch.authentication.config;

import com.contentmunch.authentication.service.MuncherUserDetailsService;
import com.contentmunch.authentication.service.TokenizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties({AuthConfigProperties.class})
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenizationService tokenizationService, MuncherUserDetailsService userDetailsService, AuthConfigProperties authConfigProperties) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login", "/api/auth/logout")
                        .permitAll().anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter(tokenizationService, userDetailsService, authConfigProperties), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    public OncePerRequestFilter jwtAuthFilter(TokenizationService tokenizationService, MuncherUserDetailsService userDetailsService, AuthConfigProperties authConfig) {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(
                    @NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    @NonNull FilterChain filterChain
            ) throws ServletException, IOException {
                String token = null;

                // Check Authorization header
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7); // Strip "Bearer "
                }

                // Fallback to cookie if Authorization header is not present
                if (token == null) {
                    for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
                        if (authConfig.cookie().name().equals(cookie.getName())) {
                            token = cookie.getValue();
                            break;
                        }
                    }
                }

                if (token != null && tokenizationService.validateToken(token)) {
                    String username = tokenizationService.extractUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

                filterChain.doFilter(request, response);
            }
        };
    }
}
