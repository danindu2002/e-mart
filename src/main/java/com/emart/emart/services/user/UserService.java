package com.emart.emart.services.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.models.User;

import java.util.List;

public interface UserService {

    int saveUser(User user);
    UserDto viewUser(Long userId);
    List<UserDto> searchUser(String keyword, Long role);
    List<UserDto> viewAllUsers(Long role);
    List<UserDto> viewAll();
    int updateUser(long userId, User user, Boolean changePwd);
    int deleteUser(long userId);
    User authenticateUser(String email, String password);
//    String saveProfilePhoto(MultipartFile profilePhoto) throws IOException;

}
