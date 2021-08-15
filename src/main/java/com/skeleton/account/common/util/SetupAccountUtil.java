package com.skeleton.account.common.util;

import com.skeleton.account.common.constant.enumerations.StaticRoles;
import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.Role;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

import static com.skeleton.account.common.constant.enumerations.StaticRoles.ADMIN;
import static com.skeleton.account.common.constant.enumerations.StaticRoles.MANAGER;
import static com.skeleton.account.common.constant.enumerations.StaticRoles.SUPER_ADMIN;
import static com.skeleton.account.common.constant.enumerations.StaticRoles.USER;

@UtilityClass
public final class SetupAccountUtil {

    public static void setupAccount(Account account, StaticRoles role) {
        Set<Role> roles = new HashSet<>();
        if (SUPER_ADMIN.equals(role)) {
            roles.add(SUPER_ADMIN.getRole());
        }
        if (ADMIN.equals(role)) {
            roles.add(ADMIN.getRole());
        }
        if (MANAGER.equals(role)) {
            roles.add(MANAGER.getRole());
        }
        if (USER.equals(role)) {
            roles.add(USER.getRole());
        }
        account.setRoles(roles);
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
