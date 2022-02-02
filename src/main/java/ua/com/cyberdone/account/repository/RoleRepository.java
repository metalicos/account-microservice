package ua.com.cyberdone.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberdone.account.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role);

    void deleteByRole(String role);

    boolean existsByRole(String role);
}
