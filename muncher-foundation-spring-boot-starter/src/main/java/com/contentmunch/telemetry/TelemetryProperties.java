package com.contentmunch.telemetry;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "contentmunch.telemetry")
public record TelemetryProperties(boolean enabled) {
}
