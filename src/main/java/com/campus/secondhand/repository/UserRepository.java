package com.campus.secondhand.repository;

import com.campus.secondhand.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}