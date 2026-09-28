package com.focusflow.demo.repository;

import com.focusflow.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>
{
    Optional<User> findByEmail(String email);
}
