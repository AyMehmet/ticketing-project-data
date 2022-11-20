package com.cydeo.service.serviceimpl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository rolerepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository rolerepository, RoleMapper roleMapper) {
        this.rolerepository = rolerepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> ListAllRoles() {
        return rolerepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(long id) {
        return roleMapper.convertToDto(rolerepository.findById(id).get());
    }


}
