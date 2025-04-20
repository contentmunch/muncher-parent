package com.contentmunch.authentication.config;

import com.contentmunch.authentication.data.MuncherUser;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


@ConfigurationProperties(prefix = "contentmunch.auth")
@Builder
public record AuthConfigProperties(int maxAgeInMinutes, String secret, CookieConfig cookie,
                                   Map<String, MuncherUser> users) {


    @Builder
    public record CookieConfig(String name, SameSite sameSite, boolean secure,
                               boolean httpOnly, String path) {


        @Getter
        public enum SameSite {
            NONE("None"), LAX("Lax"), STRICT("Strict");
            private final String value;

            SameSite(String value) {
                this.value = value;
            }

        }
    }
}
