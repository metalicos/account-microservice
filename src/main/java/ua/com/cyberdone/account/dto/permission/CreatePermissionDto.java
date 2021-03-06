package ua.com.cyberdone.account.dto.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePermissionDto {
    private String name;
    private String value;
}
