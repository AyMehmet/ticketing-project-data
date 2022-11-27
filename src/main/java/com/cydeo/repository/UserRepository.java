package com.cydeo.repository;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCase(String description);


}
