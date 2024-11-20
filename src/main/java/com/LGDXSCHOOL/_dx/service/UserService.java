package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.UserDTO;
import com.LGDXSCHOOL._dx.entity.User;
import com.LGDXSCHOOL._dx.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user); // DTO -> Entity 자동 변환

        log.info("user => {}" , user);
        log.info("userDTO => {}" , userDTO);
        User savedUser = userRepository.save(user); // 저장

        BeanUtils.copyProperties(savedUser, userDTO); // Entity -> DTO 자동 변환
        return userDTO;
    }
}
