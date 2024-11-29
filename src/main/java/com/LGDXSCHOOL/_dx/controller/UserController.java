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
        if (loginDTO.getUserId() == null || loginDTO.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().body("USER_ID is required.");
        }
        if (loginDTO.getUserPw() == null || loginDTO.getUserPw().isEmpty()) {
            return ResponseEntity.badRequest().body("USER_PW is required.");
        }

        try {
            String token = userService.login(loginDTO.getUserId(), loginDTO.getUserPw());
            return ResponseEntity.ok(new LoginResponseDTO(token)); // 로그인 성공 시 토큰 반환
        } catch (IllegalArgumentException e) {
            // Flutter에서 정확한 메시지를 처리하기 위해 명확한 에러 메시지를 반환
            String errorMessage = e.getMessage();

            if ("존재하지 않는 사용자입니다.".equals(errorMessage)) {
                return ResponseEntity.badRequest().body("등록된 이메일이 없습니다.");
            } else if ("비밀번호가 일치하지 않습니다.".equals(errorMessage)) {
                return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
            } else {
                return ResponseEntity.badRequest().body("로그인 실패");
            }
        } catch (Exception e) {
            // 서버 내부 에러 처리
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }
}
