package com.skeleton.account.dto.permission;

import com.skeleton.account.entity.Permission;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PermissionsDto {
    private Integer page;
    private Integer elementsOnThePage;
    private Integer totallyPages;
    private Integer foundElements;
    private Long totallyElements;
    private String sortedBy;
    private String sortDirection;
    private Set<Permission> permissions;
}
