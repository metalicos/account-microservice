package com.skeleton.account.dto.permission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {
    private Long id;
    private String name;
    private String value;
}
