package com.contentmunch.authentication.config;

import com.contentmunch.authentication.controller.AuthController;
import com.contentmunch.authentication.error.SecurityExceptionHandler;
import com.contentmunch.authentication.service.CookieService;
import com.contentmunch.authentication.service.MuncherUserDetailsService;
import com.contentmunch.authentication.service.TokenizationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SecurityExceptionHandler.class, SecurityConfig.class, AuthController.class, CookieService.class, MuncherUserDetailsService.class, TokenizationService.class})
public class AuthenticationAutoConfiguration {
}
