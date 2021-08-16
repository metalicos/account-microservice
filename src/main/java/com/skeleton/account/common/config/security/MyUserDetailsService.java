package com.skeleton.account.common.config.security;

import com.skeleton.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.skeleton.account.common.exception.messages.UsernameNotFoundMessage.getMessage;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(
                accountRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(getMessage(username)))
        );
    }
}
