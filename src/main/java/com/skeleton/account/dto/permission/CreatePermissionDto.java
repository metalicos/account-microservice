package com.skeleton.account.dto.permission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePermissionDto {
    private String name;
    private String value;
}
