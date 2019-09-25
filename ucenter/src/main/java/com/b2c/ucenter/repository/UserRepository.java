package com.b2c.ucenter.repository;

import com.b2c.ucenter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    UserEntity findByUsername(String username);
}
