package com.emart.emart.services.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.mappers.UserMapper;
import com.emart.emart.models.User;
import com.emart.emart.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;

    @Override
    public int saveUser(User user) {
        if(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
        {
            if(userRepo.findByEmailAndDeletedIsFalse(user.getEmail()) == null)
            {
                userRepo.save(user);
                return 0; // saved
            }
            else return 1; // duplicate email
        } else return 2; // invalid email
    }

    @Override
    public UserDto viewUser(Long userId) {
        return UserMapper.userMapper.mapToUserDto(userRepo.findByUserIdAndDeletedIsFalse(userId));
    }

    @Override
    public List<UserDto> searchUser(String keyword, String role) {
        return UserMapper.userMapper.mapToUserDtoList(userRepo.searchUsers(keyword, role));
    }

    @Override
    public List<UserDto> viewAllUsers(String role) {
        return UserMapper.userMapper.mapToUserDtoList(userRepo.viewAllUsers(role));
    }

    @Override
    public List<UserDto> viewAll() {
        return UserMapper.userMapper.mapToUserDtoList(userRepo.findAllByDeletedIsFalse());
    }

    @Override
    public int updateUser(long userId, User user) {
        User updatedUser = userRepo.findByUserIdAndDeletedIsFalse(userId);
        if (updatedUser == null) return 3; // user not found
        else
        {
            if(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            {
                User user1 = userRepo.findByEmailAndDeletedIsFalse(user.getEmail());
                if(user1 == null || user1.getUserId() == userId)
                {
                    updatedUser.setFirstName(user.getFirstName());
                    updatedUser.setLastName(user.getLastName());
                    updatedUser.setEmail(user.getEmail());
                    updatedUser.setContactNo(user.getContactNo());
                    updatedUser.setAddress(user.getAddress());
                    updatedUser.setPassword(user.getPassword());
                    if (user.getProfilePhoto() != null) updatedUser.setProfilePhoto(user.getProfilePhoto());
                    userRepo.save(updatedUser);
                    return 0; // saved
                }
                else return 1; // duplicate user found
            }
            else return 2; // invalid email
        }
    }

    @Override
    public int deleteUser(long userId) {
        User deletedUser = userRepo.findByUserIdAndDeletedIsFalse(userId);
        if(deletedUser == null) return 1; // user not found
//        else if (!itineraryRepo.searchUserId(String.valueOf(userId)).isEmpty()) {
//            return 2; // user has been assigned to itineraries
//        }
        else {
            deletedUser.setDeleted(true);
            userRepo.save(deletedUser);
            return 0; // deleted
        }
    }

    @Override
    public String authenticateUser(String email, String password) {
        return userRepo.authenticateUser(email, password);
    }

    //    public int saveUser(User user, String filePath)
//    {
//        if(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
//        {
//            if(userRepo.findByEmailAndDeletedIsFalse(user.getEmail()) == null)
//            {
//                user.setProfilePhoto(filePath);
//                userRepo.save(user);
//                return 0; // saved
//            }
//            else return 1; // duplicate email
//        } else return 2; // invalid email
//    }

//    @Override
//    public String saveProfilePhoto(MultipartFile profilePhoto) throws IOException {
//        String uploadDir = "D:/OneDrive - Informatics Holdings/Evaluation Tasks/e-mart";
//
//        File uploadDirFile = new File(uploadDir);
//        if (!uploadDirFile.exists()) {
//            uploadDirFile.mkdirs();
//        }
//
//        String fileName = UUID.randomUUID().toString() + "_" + profilePhoto.getOriginalFilename();
//
//
//        Path filePath = Paths.get(uploadDir, fileName);
//        Files.copy(profilePhoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // Return the file path
//        return "/uploads/" + fileName; // Adjust the path based on your setup
//    }
}

