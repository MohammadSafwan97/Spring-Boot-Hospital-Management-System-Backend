package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    List<User> findByClinicId(Long clinicId);
    Optional<User> findByIdAndClinicId(Long id, Long clinicId);
    Optional<User> findByUsernameAndClinicId(String username, Long clinicId);
}

