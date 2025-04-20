package com.contentmunch.logging;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(name = "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration")
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestResponseLoggingFilter requestResponseLoggingFilter() {
        return new RequestResponseLoggingFilter();
    }
}
