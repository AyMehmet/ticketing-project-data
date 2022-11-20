package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.repository.RoleRepository;

import java.util.List;

public interface RoleService  {

    List<RoleDTO>  ListAllRoles();

    RoleDTO findById(long id);
}
