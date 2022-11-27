package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> ListAllUsers();
    void save(UserDTO userDTO);
    void deleteById(String username);
    UserDTO findbyID(String username);
    void update(UserDTO userDTO);


  List<UserDTO> listAllByRole(String role);
}
