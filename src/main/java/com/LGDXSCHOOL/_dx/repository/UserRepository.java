package com.LGDXSCHOOL._dx.repository;


import com.LGDXSCHOOL._dx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
