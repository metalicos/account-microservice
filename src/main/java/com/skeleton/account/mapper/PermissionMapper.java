package com.skeleton.account.mapper;

import com.skeleton.account.entity.Permission;
import org.modelmapper.ModelMapper;

public class PermissionMapper<Dto> extends AbstractMapper<Permission, Dto> {

    public PermissionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
