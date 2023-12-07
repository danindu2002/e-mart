package com.emart.emart.services.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.models.User;

import java.util.List;

public interface UserService {
    int saveUser(User user);
    UserDto viewUser(Long userId);
    List<UserDto> searchUser(String keyword, String role);
    List<UserDto> viewAllUsers(String role);
    int updateUser(long userId, User user);
    int deleteUser(long userId);
}
