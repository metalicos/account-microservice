package com.skeleton.account.common.config.security;

import com.skeleton.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getIsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getIsNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.getIsEnabled();
    }
}
