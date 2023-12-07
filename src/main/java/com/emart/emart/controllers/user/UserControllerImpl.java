package com.emart.emart.controllers.user;

import com.emart.emart.models.User;
import com.emart.emart.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emart.emart.utility.Utility.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserControllerImpl implements UserController{
    private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try
        {
            if(userService.saveUser(user) == 0)
            {
                logger.info("User account created successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "User account created",userService.viewUser(user.getUserId())));
            }
            else if(userService.saveUser(user) == 1)
            {
                logger.info("Duplicate email found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Duplicate email found, please try again"));
            }
            else
            {
                logger.info("Invalid email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Invalid email, please try again"));
            }
        }
        catch (Exception e)
        {
            logger.error("Failed to create the user account");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Failed to create the user account"));
        }
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Object> searchUser(String keyword, String role) {
        if(!userService.searchUser(keyword, role).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Users found", userService.searchUser(keyword, role)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "no users found"));
    }

    @Override
    @GetMapping("/viewAll/{role}")
    public ResponseEntity<Object> viewAllUsers(String role) {
        if(!userService.viewAllUsers(role).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Users found", userService.viewAllUsers(role)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "no users found"));
    }

    @Override
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateUser(User user, Long userId) {
        try
        {
            if(userService.updateUser(userId, user) == 0)
            {
                logger.info("User updated successfully");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "user updated successfully",
                        userService.viewUser(userId)));
            }
            else if(userService.updateUser(userId, user) == 1)
            {
                logger.info("Duplicate email found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Duplicate email found, please try again"));
            }
            else if (userService.updateUser(userId, user) == 2)
            {
                logger.info("Invalid email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Invalid email, please try again"));
            }
            else throw new Exception();
        }
        catch (Exception e)
        {
            logger.error("User account not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "User account not found"));
        }
    }

    @Override
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser(Long userId) {
        try
        {
            if (userService.deleteUser(userId) == 0)
            {
                logger.info("User deleted");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "User with the ID : " + userId + " deleted successfully"));
            }
            else
//                if (userService.deleteUser(userId) == 1)
            {
                logger.error("User account not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "User account not found"));
            }
//            else
//            {
//                logger.warn("This user has itineraries assigned to it");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 UNAUTHORIZED", "This user has itineraries assigned to it"));
//            }
        }
        catch (Exception e)
        {
            logger.error("An error occurred while deleting the user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "An error occurred while deleting the user"));
        }
    }
}