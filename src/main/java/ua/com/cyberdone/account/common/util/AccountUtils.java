package ua.com.cyberdone.account.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.cyberdone.account.entity.Account;
import ua.com.cyberdone.account.entity.Role;

import java.util.Arrays;
import java.util.Set;

@UtilityClass
public final class AccountUtils {
    public static final String DEFAULT_ROLE = "USER";
    private static final String SPLIT_CHAR = ";";
    private static final String CREATE_USER_PERMITTED = "OWNER" + SPLIT_CHAR + "ADMIN" + SPLIT_CHAR + "SUPER_ADMIN";
    private static final String DEFAULT_USER_PHOTO_PATH = "src/main/resources/static/user.png";

    public static boolean permittedToCreateNewUser(Account creatorAccount) {
        return creatorAccount.getRoles().stream()
                .anyMatch(role -> Arrays.stream(CREATE_USER_PERMITTED.split(SPLIT_CHAR))
                        .anyMatch(p -> p.equals(role.getRole())));
    }

    public static void setupAccount(PasswordEncoder passwordEncoder, Account account, Set<Role> roles) {
        account.setRoles(roles);
        setupAccount(passwordEncoder, account);
    }

    public static void setupAccount(PasswordEncoder passwordEncoder, Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setPhoto(null);
        makeFullyAvailable(account);
    }

    private static void makeFullyAvailable(Account account) {
        setupAvailable(account, true, true, true, true);
    }

    public static void setupAvailable(Account account,
                                      boolean enabled, boolean credentialsNonExpired,
                                      boolean nonExpired, boolean nonLocked) {
        account.setIsEnabled(enabled);
        account.setIsCredentialsNonExpired(credentialsNonExpired);
        account.setIsNonExpired(nonExpired);
        account.setIsNonLocked(nonLocked);
    }
}
