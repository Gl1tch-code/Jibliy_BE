package com.gl1tch.Jibliy.service.user;

import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
