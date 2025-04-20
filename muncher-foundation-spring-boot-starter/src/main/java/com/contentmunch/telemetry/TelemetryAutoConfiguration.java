package com.contentmunch.telemetry;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(name = "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration")
public class TelemetryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TelemetryHeaderFilter telemetryHeaderFilter() {
        return new TelemetryHeaderFilter();
    }
}
