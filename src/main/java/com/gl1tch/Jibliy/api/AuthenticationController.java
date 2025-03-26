package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.InitialSignupCommand;
import com.gl1tch.Jibliy.commands.UpdateProfileCommand;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.service.auth.AuthenticationService;
import com.gl1tch.Jibliy.service.reset.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordResetService passwordResetService;

    @PostMapping("/initialSignup")
    public ResponseEntity<Long> initialSignup(@Valid @RequestBody InitialSignupCommand initialSignupRequest) {
        User user = authenticationService.registerInitialUser(initialSignupRequest);
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        User user = authenticationService.login(username, password);
        String token = jwtTokenService.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@Valid @RequestBody UpdateProfileCommand updateProfileRequest) throws NoSuchAlgorithmException {
        User user = authenticationService.updateUserProfile(updateProfileRequest);
        String token = jwtTokenService.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
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
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp, @RequestBody String newPassword) {
        try {
            passwordResetService.verifyOtpAndResetPassword(email, otp, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred.");
        }
    }
}