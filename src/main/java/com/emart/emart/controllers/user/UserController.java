package com.emart.emart.controllers.user;

import com.emart.emart.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController {
    ResponseEntity<Object> createUser(@RequestBody User user);
    ResponseEntity<Object> searchUser(@RequestParam String keyword, @RequestParam String role);
    ResponseEntity<Object> viewAllUsers(@PathVariable String role);
    ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long userId);
    ResponseEntity<Object> deleteUser(@PathVariable Long userId);
}
