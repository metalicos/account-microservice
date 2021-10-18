package ua.com.cyberdone.account.service;

import ua.com.cyberdone.account.common.exception.AlreadyExistException;
import ua.com.cyberdone.account.common.exception.NotFoundException;
import ua.com.cyberdone.account.dto.permission.PermissionDto;
import ua.com.cyberdone.account.dto.permission.PermissionsDto;
import ua.com.cyberdone.account.entity.Permission;
import ua.com.cyberdone.account.mapper.PermissionMapper;
import ua.com.cyberdone.account.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public PermissionsDto getAllPermissions() throws NotFoundException {
        var permissions = Optional.of(permissionRepository.findAll()).orElseThrow(
                () -> new NotFoundException("Permission not found"));
        log.info("Permissions {} are successfully read", permissions);
        return PermissionsDto.builder().permissions(new HashSet<>(permissions)).build();
    }

    public PermissionsDto getAllPermissions(int page, int size, String direction, String sortBy) {
        var sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        var permissionPage = permissionRepository.findAll(PageRequest.of(page, size, sort));
        log.info("Permissions {} are successfully read", permissionPage.getContent());
        return PermissionsDto.builder()
                .page(page)
                .elementsOnThePage(size)
                .totallyPages(permissionPage.getTotalPages())
                .foundElements(permissionPage.getNumberOfElements())
                .totallyElements(permissionPage.getTotalElements())
                .sortedBy(sortBy)
                .sortDirection(direction)
                .permissions(permissionPage.toSet())
                .build();
    }

    public PermissionDto getPermission(String name) throws NotFoundException {
        var permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        log.info("Permission {} is successfully read", permission);
        return new PermissionMapper<PermissionDto>(modelMapper).toDto(permission, PermissionDto.class);
    }

    public PermissionDto createPermission(String name, String value) throws NotFoundException, AlreadyExistException {
        if (permissionRepository.existsByName(name)) {
            throw new AlreadyExistException("Permission already exists");
        }
        var saved = permissionRepository.save(new Permission(name, value));
        log.info("Permission {} is successfully created", saved);
        return new PermissionMapper<PermissionDto>(modelMapper).toDto(saved, PermissionDto.class);
    }

    public void deletePermission(String name) {
        permissionRepository.deleteByName(name);
        log.info("Permission {} is deleted", name);
    }

    public void deleteAllPermission() {
        permissionRepository.deleteAll();
        log.info("All Permissions are deleted");
    }
}
