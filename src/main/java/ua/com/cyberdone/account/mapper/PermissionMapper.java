package ua.com.cyberdone.account.mapper;

import ua.com.cyberdone.account.entity.Permission;
import org.modelmapper.ModelMapper;

public class PermissionMapper<Dto> extends AbstractMapper<Permission, Dto> {

    public PermissionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
