package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.dto.UserResponseDto;
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

    /* ---------------- GET ALL USERS ---------------- */

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam Long clinicId) {
        return ResponseEntity.ok(userService.getAllUsers(clinicId));
    }

    /* ---------------- CREATE USER ---------------- */

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto) {

        try {

            UserResponseDto savedUser = userService.createUser(userDto);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest().build();

        }
    }

    /* ---------------- GET USER BY USERNAME ---------------- */

    @GetMapping("/username/{name}")
    public ResponseEntity<UserDto> getUserByName(@RequestParam Long clinicId, @PathVariable String name) {
        return ResponseEntity.ok(userService.findByUsername(clinicId, name));
    }

    /* ---------------- GET USER BY ID ---------------- */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long clinicId, @PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(clinicId, userId));
    }

    /* ---------------- UPDATE USER ---------------- */

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestParam Long clinicId,
            @PathVariable Long id,
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.updateUser(clinicId, id, userDto));
    }

    /* ---------------- DELETE USER ---------------- */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long clinicId, @PathVariable Long id) {
        userService.deleteUser(clinicId, id);
        return ResponseEntity.noContent().build();
    }

}
