package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.InitialSignupCommand;
import com.gl1tch.Jibliy.commands.UpdateProfileCommand;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.dto.AuthDTO;
import com.gl1tch.Jibliy.service.auth.AuthenticationService;
import com.gl1tch.Jibliy.service.reset.PasswordResetService;
import com.gl1tch.Jibliy.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/initialSignup")
    public ResponseEntity<Object> initialSignup(@Valid @RequestBody InitialSignupCommand initialSignupRequest) {
        try {
            User user = authenticationService.registerInitialUser(initialSignupRequest);
            return ResponseEntity.ok(user.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<AuthDTO> updateProfile(@Valid @RequestBody UpdateProfileCommand updateProfileRequest) throws NoSuchAlgorithmException {
        updateProfileRequest.isValidPhoneNumber();
        User user = authenticationService.updateUserProfile(updateProfileRequest);
        String token = jwtTokenService.generateToken(user.getEmail());
        AuthDTO authDTO = AuthDTO.builder().email(user.getEmail()).username(user.getFullName()).token(token).city(user.getCity()).build();

        return ResponseEntity.ok(authDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        User user = authenticationService.login(username, password);
        String token = jwtTokenService.generateToken(user.getEmail());
        AuthDTO authDTO = AuthDTO.builder().email(user.getEmail()).role(user.getRole()).username(user.getFullName()).token(token).city(user.getCity()).build();

        return ResponseEntity.ok(authDTO);
    }

    @PostMapping("/otp-request")
    public ResponseEntity<String> requestOtp(@RequestBody String email) {
        try {
            passwordResetService.requestOtp(email);
            return ResponseEntity.ok("OTP sent to your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred.");
        }
    }

    @PostMapping("/otp-verify")
    public ResponseEntity<AuthDTO> verifyOtp(@RequestParam String email, @RequestParam String otp, @RequestBody String newPassword) throws NoSuchAlgorithmException {
        String token = jwtTokenService.generateToken(passwordResetService.verifyOtpAndResetPassword(email, otp, newPassword));
        User user = userService.findByEmail(email);
        AuthDTO authDTO = AuthDTO.builder().email(user.getEmail()).username(user.getFullName()).token(token).city(user.getCity()).build();
        return ResponseEntity.ok(authDTO);
    }
}