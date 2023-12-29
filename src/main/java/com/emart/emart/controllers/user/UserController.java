package com.emart.emart.controllers.user;

import com.emart.emart.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserController {

//    ResponseEntity<Object> createUser(@RequestPart User user, @RequestPart MultipartFile profilePhoto);
    ResponseEntity<Object> createUser(@RequestBody User user);
    ResponseEntity<Object> searchUser(@RequestParam String keyword, @RequestParam Long role, @PathVariable Long userId);
    ResponseEntity<Object> viewById(@PathVariable Long userId);
    ResponseEntity<Object> viewAllUsers(@PathVariable Long role, @PathVariable Long userId);
    ResponseEntity<Object> viewAll(@PathVariable Long userId);
    ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long userId, @PathVariable Boolean changePwd);
//    ResponseEntity<Object> updateUser(@RequestPart User user, @PathVariable Long userId, @PathVariable Boolean changePwd, @RequestPart MultipartFile profilePhoto);
    ResponseEntity<Object> deleteUser(@PathVariable Long userId);
    ResponseEntity<Object> authenticateUser(@RequestParam String email, @RequestParam String password);
}
