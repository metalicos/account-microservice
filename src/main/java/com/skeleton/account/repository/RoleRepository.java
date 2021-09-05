package com.skeleton.account.repository;

import com.skeleton.account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role);

    void deleteByRole(String role);

    boolean existsByRole(String role);
}
