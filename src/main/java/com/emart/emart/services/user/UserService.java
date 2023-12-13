package com.emart.emart.services.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

//    int saveUser(User user, String filePath);
    int saveUser(User user, MultipartFile profilePhoto);
    UserDto viewUser(Long userId);
    List<UserDto> searchUser(String keyword, Long role);
    List<UserDto> viewAllUsers(Long role);
    List<UserDto> viewAll();
    int updateUser(long userId, User user, Boolean changePwd, MultipartFile profilePhoto) throws IOException;
    int deleteUser(long userId);
    User authenticateUser(String email, String password);
    String saveProfilePhoto(MultipartFile profilePhoto) throws IOException;

}
