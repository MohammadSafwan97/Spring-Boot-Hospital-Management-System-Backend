package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.dto.UserResponseDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final CurrentUserUtil currentUserUtil;

    /* ---------------- GET ALL USERS ---------------- */

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers(currentUserUtil.getCurrentClinicId()));
    }

    /* ---------------- CREATE USER ---------------- */

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto) {

        try {
            userDto.setClinicId(currentUserUtil.getCurrentClinicId());

            UserResponseDto savedUser = userService.createUser(userDto);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest().build();

        }
    }

    /* ---------------- GET USER BY USERNAME ---------------- */

    @GetMapping("/username/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByUsername(currentUserUtil.getCurrentClinicId(), name));
    }

    /* ---------------- GET USER BY ID ---------------- */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(currentUserUtil.getCurrentClinicId(), userId));
    }

    /* ---------------- UPDATE USER ---------------- */

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto
    ) {
        userDto.setClinicId(currentUserUtil.getCurrentClinicId());
        return ResponseEntity.ok(userService.updateUser(currentUserUtil.getCurrentClinicId(), id, userDto));
    }

    /* ---------------- DELETE USER ---------------- */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(currentUserUtil.getCurrentClinicId(), id);
        return ResponseEntity.noContent().build();
    }

}
