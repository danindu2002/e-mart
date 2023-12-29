package com.emart.emart.controllers.user;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.mappers.UserMapper;
import com.emart.emart.models.User;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.services.user.UserService;
import com.emart.emart.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emart.emart.utility.Utility.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserControllerImpl implements UserController{
    private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private Utility utility;
    @Autowired
    private UserRepo userRepo;

    @Override
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            if (userService.saveUser(user) == 0) {
                logger.info("User account created successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "User account created", userService.viewUser(user.getUserId())));
            }
            else if (userService.saveUser(user) == 1) {
                logger.info("Duplicate email found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Duplicate email found, please try again"));
            }
            else if (userService.saveUser(user) == 2) {
                logger.info("Invalid contact number");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Invalid contact number, please try again"));
            }
            else {
                logger.info("Invalid email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Invalid email, please try again"));
            }
        } catch (Exception e) {
            logger.error("Failed to create the user account", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Failed to create the user account"));
        }
    }
    @Override
    @GetMapping("/search-users/{userId}")
    public ResponseEntity<Object> searchUser(String keyword, Long role, Long userId) {
        if(utility.authorization(userId)) {
        if(!userService.searchUser(keyword, role).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Users found", userService.searchUser(keyword, role)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No users found"));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
        }

    @Override
    @GetMapping("/view-users/{userId}")
    public ResponseEntity<Object> viewById(Long userId) {
        UserDto user = userService.viewUser(userId);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No users found"));
        else {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "User found", user));
        }
    }

    @Override
    @GetMapping("/view-user-roles/{role}/{userId}")
    public ResponseEntity<Object> viewAllUsers(Long role,Long userId) {
        if(utility.authorization(userId)) {
        if(!userService.viewAllUsers(role).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Users found", userService.viewAllUsers(role)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No users found"));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
        }

    @Override
    @GetMapping("/view-All-users/{userId}")
    public ResponseEntity<Object> viewAll(@PathVariable Long userId) {
        if(utility.authorization(userId)) {
            if (!userService.viewAll().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "All users found", userService.viewAll()));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No users found"));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @Override
    @PutMapping("/{userId}/{changePwd}")
    public ResponseEntity<Object> updateUser(User user, Long userId, Boolean changePwd)
    {
        try {
            if (userService.updateUser(userId, user, changePwd) == 0) {
                logger.info("User updated successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "User updated successfully", userService.viewUser(userId)));
            } else if (userService.updateUser(userId, user, changePwd) == 1) {
                logger.info("Duplicate email found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(convertToResponseMsgDto("406 Not Acceptable", "Duplicate email found, please try again"));
            } else if (userService.updateUser(userId, user, changePwd) == 2) {
                logger.info("Invalid email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(convertToResponseMsgDto("406 Not Acceptable", "Invalid email, please try again"));
            } else {
                logger.info("Invalid contact number");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(convertToResponseMsgDto("406 Not Acceptable", "Invalid contact number, please try again"));
            }
        } catch (Exception e) {
            logger.error("User account not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(convertToResponseMsgDto("404 Not Found", "User account not found"));
        }
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(Long userId) {
        try
        {
            if (userService.deleteUser(userId) == 0)
            {
                logger.info("User deleted");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "User deleted successfully"));
            }
            else {
                logger.error("User account not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "User account not found"));
            }
        }
        catch (Exception e)
        {
            logger.error("An error occurred while deleting the user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "An error occurred while deleting the user"));
        }
    }

    @Override
    @PostMapping("/authenticate-users")
    public ResponseEntity<Object> authenticateUser(String email, String password) {
        try {
            User user = userService.authenticateUser(email, password);

            if (userRepo.findByEmailAndDeletedIsFalse(email) != null){
                if (user != null) {
                    logger.info("Authenticated as "+ user.getRole() +" successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Authenticated as "+ user.getRole() +" successfully", UserMapper.userMapper.mapToUserDto(user)));
                }
                else {
                    logger.info("Incorrect credentials");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseItemDto("404 Not Found", "Incorrect credentials", ""));
                }
            }
            else throw new Exception();
        }
        catch (Exception e) {
            logger.info("User not found, Incorrect email", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "User not found, Incorrect email"));
        }
    }
}