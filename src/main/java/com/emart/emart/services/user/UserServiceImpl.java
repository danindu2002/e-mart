package com.emart.emart.services.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.mappers.UserMapper;
import com.emart.emart.models.User;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.repositories.reference.RefRoleRepo;
import com.emart.emart.security.AESConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefRoleRepo refRoleRepo;
    @Autowired
    private AESConverter aesConverter;

    @Override
    public int saveUser(User user) {
        try{
            if(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            {
                if(user.getContactNo().matches("^\\+?\\d{10,}$"))
                {
                    if (userRepo.findByEmailAndDeletedIsFalse(user.getEmail()) == null) {
                        user.setRole(refRoleRepo.findRefRoleByRefRoleId(Long.parseLong(user.getRole())).getRefRoleName());
                        user.setPassword(aesConverter.convertToDatabaseColumn(user.getPassword()));

                        userRepo.save(user);
                        logger.info("user saved");
                        return 0;
                    } else {
                        logger.info("duplicate email found");
                        return 1;
                    }
                }
                else {
                    logger.error("Invalid contact number");
                    return 2;
                }
            } else {
                logger.info("invalid email");
                return 3;
            }
        }
        catch (Exception e) {
            return 4;
        }
    }

    @Override
    public UserDto viewUser(Long userId) {
        logger.info("fetched user data");
        return UserMapper.userMapper.mapToUserDto(userRepo.findByUserIdAndDeletedIsFalse(userId));
    }

    @Override
    public List<UserDto> searchUser(String keyword, Long role) {
        String roleName = refRoleRepo.findRefRoleByRefRoleId(role).getRefRoleName();
        logger.info("search results found");
        return UserMapper.userMapper.mapToUserDtoList(userRepo.searchUsers(keyword, roleName));
    }

    @Override
    public List<UserDto> viewAllUsers(Long role) {
        String roleName = refRoleRepo.findRefRoleByRefRoleId(role).getRefRoleName();
        logger.info("fetched all users relevant to role");
        return UserMapper.userMapper.mapToUserDtoList(userRepo.viewAllUsers(roleName));
    }

    @Override
    public List<UserDto> viewAll() {
        logger.info("fetched all users");
        return UserMapper.userMapper.mapToUserDtoList(userRepo.findAllByDeletedIsFalse());
    }

    @Override
    public int updateUser(long userId, User user, Boolean changePwd) {
        User updatedUser = userRepo.findByUserIdAndDeletedIsFalse(userId);
        if (updatedUser == null) {
            logger.info("User not found");
            return 3;
        }
        else
        {
            if(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            {
                if(user.getContactNo().matches("^\\+?\\d{10,}$"))
                {
                    User user1 = userRepo.findByEmailAndDeletedIsFalse(user.getEmail());
                    if (user1 == null || user1.getUserId() == userId) {
                        updatedUser.setFirstName(user.getFirstName());
                        updatedUser.setLastName(user.getLastName());
                        updatedUser.setEmail(user.getEmail());
                        updatedUser.setContactNo(user.getContactNo());
                        updatedUser.setAddress(user.getAddress());
                        if (changePwd)
                            updatedUser.setPassword(aesConverter.convertToDatabaseColumn(user.getPassword()));
                        if (user.getProfilePhoto() != null) updatedUser.setProfilePhoto(user.getProfilePhoto());
                        userRepo.save(updatedUser);

                        logger.info("User updated");
                        return 0;
                    } else {
                        logger.info("Duplicate email found");
                        return 1;
                    }
                } else {
                    logger.info("invalid contact number");
                    return 3;
                }
            }
            else {
                logger.info("invalid email");
                return 2;
            }
        }
    }

    @Override
    public int deleteUser(long userId) {
        User deletedUser = userRepo.findByUserIdAndDeletedIsFalse(userId);
        if(deletedUser == null) {
            logger.error("user not found");
            return 1;
        }
//        else if (!itineraryRepo.searchUserId(String.valueOf(userId)).isEmpty()) {
//            return 2; // user has been assigned to itineraries
//        }
        else {
            deletedUser.setDeleted(true);
            userRepo.save(deletedUser);
            logger.info("user deleted");
            return 0;
        }
    }


    /* Password conversion
        Creating user => plain text > base64(FE) > encryption(BE) > save
        Updating user => plain text > base64(FE) > encryption(BE) > update
        Authenticating user =>  plain text > base64(FE) > encryption(BE) > checking (encrypted saved password == encrypted user entered password)
    */


    @Override
    public User authenticateUser(String email, String password) {
        User user = userRepo.findByEmailAndDeletedIsFalse(email);

        // User entered base64 password to encrypted password conversion
        String encryptedPassword = aesConverter.convertToDatabaseColumn(password);

        // Checking that both encrypted passwords are same or not
        if (user.getPassword().equals(encryptedPassword)) {
            logger.info("User authenticated");
            return user;
        }
        logger.error("Authentication failed");
        return null;
    }
}

