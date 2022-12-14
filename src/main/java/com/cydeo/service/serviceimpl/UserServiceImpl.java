package com.cydeo.service.serviceimpl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> ListAllUsers() {
        return userRepository.findAll().stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public void deleteById(String username) {
        userRepository.delete(userRepository.findUserByUserName(username));
    }

    @Override
    public UserDTO findbyID(String username) {
        return userMapper.convertToDto(userRepository.findUserByUserName(username));
    }

    @Override
    public void update(UserDTO userDTO) {

        User currentUser=userRepository.findUserByUserName(userDTO.getUserName());
        User updatedUser=userMapper.convertToEntity(userDTO);
        updatedUser.setId(currentUser.getId());

        userRepository.save(updatedUser);
    }


}
