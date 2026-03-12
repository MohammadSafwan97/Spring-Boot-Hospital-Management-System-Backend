package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();

    }

    /* ---------------- CREATE USER ---------------- */

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {

        return userService.createUser(userDto);

    }

    /* ---------------- UPDATE USER ---------------- */

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto
    ) {

        return userService.updateUser(id, userDto);

    }

    /* ---------------- DELETE USER ---------------- */

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

    }

}