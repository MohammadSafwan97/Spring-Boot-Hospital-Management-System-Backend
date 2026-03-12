package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
