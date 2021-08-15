package com.skeleton.account.common.util;

import com.skeleton.account.common.constant.StaticRoles;
import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.Role;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

import static com.skeleton.account.common.constant.StaticRoles.ADMIN;
import static com.skeleton.account.common.constant.StaticRoles.MANAGER;
import static com.skeleton.account.common.constant.StaticRoles.SUPER_ADMIN;
import static com.skeleton.account.common.constant.StaticRoles.USER;

@UtilityClass
public final class SetupAccountUtil {

    public static Account setupAccount(Account account, StaticRoles role) {
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
        return makeFullyAvailable(account);
    }

    private static Account makeFullyAvailable(Account account) {
        return setupAvailable(account, true, true, true, true);
    }

    public static Account setupAvailable(Account account,
                                         boolean enabled, boolean credentialsNonExpired,
                                         boolean nonExpired, boolean nonLocked) {
        account.setIsEnabled(true);
        account.setIsCredentialsNonExpired(true);
        account.setIsNonExpired(true);
        account.setIsNonLocked(true);
        return account;
    }
}
