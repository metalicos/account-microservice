package com.skeleton.account.dto.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDto {
    private Long id;
    private String name;
    private String value;
}
