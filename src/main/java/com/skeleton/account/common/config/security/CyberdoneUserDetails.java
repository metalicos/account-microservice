package com.skeleton.account.common.config.security;

import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CyberdoneUserDetails implements UserDetails {

    public static final String SPLIT_CHAR = ",";
    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var permissions = new HashSet<SimpleGrantedAuthority>();
        for (Role role : account.getRoles()) {
            permissions.addAll(toSetAuthorities(role.getReadPermissions()));
            permissions.addAll(toSetAuthorities(role.getWritePermissions()));
            permissions.addAll(toSetAuthorities(role.getUpdatePermissions()));
            permissions.addAll(toSetAuthorities(role.getDeletePermissions()));
        }
        return permissions;
    }

    private Set<SimpleGrantedAuthority> toSetAuthorities(String permissions) {
        return Arrays.stream(permissions.split(SPLIT_CHAR)).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
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
