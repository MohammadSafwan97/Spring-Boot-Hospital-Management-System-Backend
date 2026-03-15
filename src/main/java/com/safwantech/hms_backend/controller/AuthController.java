package com.safwantech.hms_backend.controller;


import com.safwantech.hms_backend.dto.LoginRequestDto;
import com.safwantech.hms_backend.dto.LoginResponseDto;
import com.safwantech.hms_backend.dto.SignupRequestDto;
import com.safwantech.hms_backend.dto.SignupResponseDto;
import com.safwantech.hms_backend.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupResponseDto){
        return ResponseEntity.ok(authService.signup(signupResponseDto));
    }
}
