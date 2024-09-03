package com.hari.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hari.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
