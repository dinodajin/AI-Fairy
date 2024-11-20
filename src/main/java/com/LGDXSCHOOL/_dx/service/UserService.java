package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.UserDTO;
import com.LGDXSCHOOL._dx.entity.User;
import com.LGDXSCHOOL._dx.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        // 이메일 형식 검증
        if (!isValidEmail(userDTO.getUserId())) {
            throw new IllegalArgumentException("USER_ID는 유효한 이메일 형식이어야 합니다.");
        }

        // 중복 체크
        Optional<User> existingUser = userRepository.findByUserId(userDTO.getUserId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 USER_ID입니다.");
        }


        User user = new User();
        BeanUtils.copyProperties(userDTO, user); // DTO -> Entity 자동 변환
//        log.info("user => {}" , user);
//        log.info("userDTO => {}" , userDTO);
        User savedUser = userRepository.save(user); // 저장
        BeanUtils.copyProperties(savedUser, userDTO); // Entity -> DTO 자동 변환
        return userDTO;
    }
}
