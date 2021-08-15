package com.skeleton.account.common.constant;

import com.skeleton.account.entity.Role;

public enum StaticRoles {
    SUPER_ADMIN(init("SUPER_ADMIN")),
    ADMIN(init("ADMIN")),
    MANAGER(init("MANAGER")),
    USER(init("USER"));

    private Role role;

    StaticRoles(Role role) {
        this.role = role;
    }

    private static Role init(String roleStr) {
        Role role = new Role();
        role.setRole(roleStr);
        return role;
    }

    public Role getRole() {
        return role;
    }
}
