package com.skeleton.account.controller;

import com.skeleton.account.common.exception.AlreadyExistException;
import com.skeleton.account.common.exception.NotFoundException;
import com.skeleton.account.dto.permission.CreatePermissionDto;
import com.skeleton.account.dto.permission.PermissionDto;
import com.skeleton.account.dto.permission.PermissionsDto;
import com.skeleton.account.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.skeleton.account.common.constant.ControllerConstantUtils.DEFAULT_DIRECTION;
import static com.skeleton.account.common.constant.ControllerConstantUtils.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<PermissionsDto> readAllPermissions() throws NotFoundException {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPermissions() {
        permissionService.deleteAllPermission();
        return ResponseEntity.ok(OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PermissionDto> readPermission(@PathVariable("name") String name) throws NotFoundException {
        return ResponseEntity.ok(permissionService.getPermission(name));
    }

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody CreatePermissionDto dto)
            throws NotFoundException, AlreadyExistException {
        return ResponseEntity.ok(permissionService.createPermission(dto.getName(), dto.getValue()));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deletePermission(@PathVariable("name") String name) {
        permissionService.deletePermission(name);
        return ResponseEntity.ok(OK);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<PermissionsDto> readPermissions(@PathVariable("page") Integer page,
                                                          @PathVariable("size") Integer size) throws NotFoundException {
        return ResponseEntity.ok(permissionService.getAllPermissions(page, size));
    }

    @GetMapping("/page/{page}/size/{size}/sort-by/{sort-by}")
    public ResponseEntity<PermissionsDto> readPermissions(@PathVariable("page") Integer page,
                                                          @PathVariable("size") Integer size,
                                                          @PathVariable("sort-by") String sortBy) {
        return ResponseEntity.ok(permissionService.getAllPermissions(page, size, DEFAULT_DIRECTION, sortBy));
    }

    @GetMapping("/page/{page}/size/{size}/sort-by/{sort-by}/direction/{direction}")
    public ResponseEntity<PermissionsDto> readPermissions(@PathVariable("page") Integer page,
                                                          @PathVariable("size") Integer size,
                                                          @PathVariable("sort-by") String sortBy,
                                                          @PathVariable("direction") String direction) {
        return ResponseEntity.ok(permissionService.getAllPermissions(page, size, direction, sortBy));
    }
}
