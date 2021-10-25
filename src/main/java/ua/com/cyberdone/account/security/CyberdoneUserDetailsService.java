package ua.com.cyberdone.account.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.cyberdone.account.common.exception.messages.UsernameNotFoundMessage;
import ua.com.cyberdone.account.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class CyberdoneUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CyberdoneUserDetails(accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UsernameNotFoundMessage.getMessage(username))));
    }
}
