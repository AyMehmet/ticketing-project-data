package com.cydeo.service.serviceimpl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
public class RoleSeviceImpl implements RoleService {

    private final RoleRepository rolerepository;

    public RoleSeviceImpl(RoleRepository rolerepository) {
        this.rolerepository = rolerepository;
    }

    @Override
    public List<RoleDTO> ListAllRoles() {
        return rolerepository.findAll().
    }


}
