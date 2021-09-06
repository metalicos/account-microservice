package com.skeleton.account.repository;

import com.skeleton.account.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);
}
