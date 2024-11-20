package com.LGDXSCHOOL._dx.controller;


import com.LGDXSCHOOL._dx.dto.LoginDTO;
import com.LGDXSCHOOL._dx.dto.LoginResponseDTO;
import com.LGDXSCHOOL._dx.dto.UserDTO;
import com.LGDXSCHOOL._dx.entity.User;
import com.LGDXSCHOOL._dx.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
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


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.login(loginDTO.getUserId(), loginDTO.getUserPw());
            return ResponseEntity.ok(new LoginResponseDTO(token)); // LoginResponseDTO 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}