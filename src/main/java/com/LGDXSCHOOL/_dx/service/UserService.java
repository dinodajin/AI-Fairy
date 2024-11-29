package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.UserDTO;
import com.LGDXSCHOOL._dx.entity.User;
import com.LGDXSCHOOL._dx.repository.UserRepository;
import com.LGDXSCHOOL._dx.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider; // JWT 생성
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private boolean isValidEmail(String userId) {
        return Pattern.matches(EMAIL_REGEX, userId);
    }

    public boolean isEmailTaken(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public UserDTO saveUser(UserDTO userDTO) {
        // 이메일 형식 검증
        if (!isValidEmail(userDTO.getUserId())) {
            throw new IllegalArgumentException("USER_ID는 유효한 이메일 형식이어야 합니다.");
        }

        // 이메일 중복 확인
        if (isEmailTaken(userDTO.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDTO.getUserPw());
        userDTO.setUserPw(encodedPassword); // 암호화된 비밀번호를 DTO에 설정

        User user = new User();
        BeanUtils.copyProperties(userDTO, user); // DTO -> Entity 자동 변환
//        log.info("user => {}" , user);
//        log.info("userDTO => {}" , userDTO);
        User savedUser = userRepository.save(user); // 저장
        BeanUtils.copyProperties(savedUser, userDTO); // Entity -> DTO 자동 변환
        return userDTO;
    }

    // 로그인 로직
    public String login(String userId, String userPw) {
        log.info("Authenticating userId: {}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(userPw, user.getUserPw())) {
            log.warn("Password mismatch for userId: {}", userId);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(user.getUserId());
        log.info("Token created for userId: {}", userId);
        return token;
    }
}
