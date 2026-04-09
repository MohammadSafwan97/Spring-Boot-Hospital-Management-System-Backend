package com.safwantech.hms_backend.security;


import com.safwantech.hms_backend.dto.LoginRequestDto;
import com.safwantech.hms_backend.dto.LoginResponseDto;
import com.safwantech.hms_backend.dto.SignupRequestDto;
import com.safwantech.hms_backend.dto.SignupResponseDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.User;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;

    // LOGIN
    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId(), user.getClinic().getId());
    }


    public SignupResponseDto signup(SignupRequestDto signupRequestDto){

        User existingUser =
                userRepository.findByUsername(signupRequestDto.getUsername())
                        .orElse(null);

        if(existingUser != null){
            throw new IllegalArgumentException("User already exists");
        }

        Clinic clinic = clinicRepository.findById(signupRequestDto.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + signupRequestDto.getClinicId()));

        User user = User.builder()
                .clinic(clinic)
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .email(signupRequestDto.getEmail())
                .role(signupRequestDto.getRole())
                .active(signupRequestDto.getActive())

                .build();

        user = userRepository.save(user);

        return new SignupResponseDto(
                user.getId(),
                user.getClinic().getId(),
                user.getUsername()
        );
    }
}
