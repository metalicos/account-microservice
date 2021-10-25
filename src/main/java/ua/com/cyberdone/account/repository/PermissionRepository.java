package ua.com.cyberdone.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberdone.account.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);
}
