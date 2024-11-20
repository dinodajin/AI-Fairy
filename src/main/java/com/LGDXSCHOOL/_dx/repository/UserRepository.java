package com.LGDXSCHOOL._dx.repository;


import com.LGDXSCHOOL._dx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
