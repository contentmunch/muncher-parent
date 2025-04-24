package com.contentmunch.authentication.config;

import org.springframework.context.annotation.Import;

import com.contentmunch.authentication.controller.AuthController;
import com.contentmunch.authentication.error.SecurityExceptionHandler;
import com.contentmunch.authentication.service.CookieService;
import com.contentmunch.authentication.service.MuncherUserDetailsService;
import com.contentmunch.authentication.service.TokenizationService;

@Import({SecurityExceptionHandler.class, SecurityConfig.class, AuthController.class, CookieService.class,
        MuncherUserDetailsService.class, TokenizationService.class})
public class AuthenticationAutoConfiguration {
}
