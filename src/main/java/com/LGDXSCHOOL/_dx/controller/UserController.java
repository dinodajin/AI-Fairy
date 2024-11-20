package com.LGDXSCHOOL._dx.controller;


import com.LGDXSCHOOL._dx.dto.UserDTO;
import com.LGDXSCHOOL._dx.entity.User;
import com.LGDXSCHOOL._dx.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getUserId() == null || userDTO.getUserId().isEmpty()) {
            throw new IllegalArgumentException("USER_ID is required.");
        }
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }


//    @GetMapping
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
//        return ResponseEntity.ok(userService.findByUserId(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
