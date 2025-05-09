package com.gl1tch.Jibliy.service.auth;

import com.gl1tch.Jibliy.commands.InitialSignupCommand;
import com.gl1tch.Jibliy.commands.UpdateProfileCommand;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerInitialUser(InitialSignupCommand initialSignupRequest) {

        if (userRepository.existsByEmail(initialSignupRequest.getEmail())){
            throw new IllegalArgumentException("هذا البريد موجود، المرجو تسجيل الدخول");
        }
        User user = new User();
        user.setEmail(initialSignupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(initialSignupRequest.getPassword()));

        return userRepository.save(user);
    }

    public User updateUserProfile(UpdateProfileCommand updateProfileRequest) {
        User user = userRepository.findById(updateProfileRequest.getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (userRepository.existsByUsername(updateProfileRequest.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        user.setUsername(updateProfileRequest.getUsername());
        user.setFullName(updateProfileRequest.getFullName());
        user.setCity(updateProfileRequest.getCity());
        user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        user.setLocation(updateProfileRequest.getLocation());

        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new IllegalStateException("Invalid password");
        }
    }
}