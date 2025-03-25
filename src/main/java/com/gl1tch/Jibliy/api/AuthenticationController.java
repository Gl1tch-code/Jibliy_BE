package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.InitialSignupCommand;
import com.gl1tch.Jibliy.commands.UpdateProfileCommand;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.security.JwtTokenService;
import com.gl1tch.Jibliy.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenService jwtTokenService;

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
}