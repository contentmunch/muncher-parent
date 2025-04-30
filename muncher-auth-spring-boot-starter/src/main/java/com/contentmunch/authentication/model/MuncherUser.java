package com.contentmunch.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record MuncherUser(
        String name, String email, Set<MuncherRole> roles, String username,
        @JsonIgnore String password
) implements UserDetails {

    public MuncherUser {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
    }

    public static class MuncherUserBuilder {
        public MuncherUserBuilder roles(Set<MuncherRole> roles) {
            this.roles = roles == null ? Set.of() : Set.copyOf(roles);
            return this;
        }
    }

    @Override
    @JsonIgnore
    public Set<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
