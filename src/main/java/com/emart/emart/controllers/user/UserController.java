package com.emart.emart.controllers.user;

import com.emart.emart.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface UserController {

    //    ResponseEntity<Object> createUser(@RequestParam("profilePhoto") MultipartFile profilePhoto, @ModelAttribute User user);
    ResponseEntity<Object> createUser(@RequestBody User user);
    ResponseEntity<Object> searchUser(@RequestParam String keyword, @RequestParam Long role);
    ResponseEntity<Object> viewAllUsers(@PathVariable Long role);
    ResponseEntity<Object> viewAll();
    ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long userId, @PathVariable Boolean changePwd);
    ResponseEntity<Object> deleteUser(@PathVariable Long userId);
    ResponseEntity<Object> authenticateUser(@RequestParam String email, @RequestParam String password);
}
