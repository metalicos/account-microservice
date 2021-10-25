package ua.com.cyberdone.account.mapper;

import org.modelmapper.ModelMapper;
import ua.com.cyberdone.account.entity.Permission;

public class PermissionMapper<Dto> extends AbstractMapper<Permission, Dto> {

    public PermissionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
