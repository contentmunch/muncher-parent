package com.contentmunch.authentication.controller;


import com.contentmunch.authentication.data.AuthRequest;
import com.contentmunch.authentication.data.MuncherUser;
import com.contentmunch.authentication.service.CookieService;
import com.contentmunch.authentication.service.TokenizationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final CookieService cookieService;
    private final TokenizationService tokenizationService;

    @PostMapping("/login")
    public ResponseEntity<MuncherUser> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));


        var userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        if (userDetails instanceof MuncherUser muncherUser) {
            String token = tokenizationService.generateToken(muncherUser);
            var cookie = cookieService.cookieFromToken(token).toString();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie);

            log.info("User {} logged in", authRequest.username());

            return ResponseEntity.ok(muncherUser);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + authRequest.username());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        var cookie = cookieService.cookieFromToken("", 0).toString();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/me")
    public ResponseEntity<MuncherUser> getProtected() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        if (principal instanceof MuncherUser muncherUser) {
            return ResponseEntity.ok(muncherUser);

        } else {
            throw new UsernameNotFoundException("User not found with username: " + principal);
        }
    }
}
