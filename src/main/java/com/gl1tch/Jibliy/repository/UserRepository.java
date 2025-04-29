package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT u FROM User u
            WHERE LOWER(REPLACE(u.username, ' ', '')) = LOWER(REPLACE(:username, ' ', ''))
                OR LOWER(REPLACE(u.email, ' ', '')) = LOWER(REPLACE(:username, ' ', ''))
                OR LOWER(REPLACE(u.fullName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:username, ' ', ''), '%'))
                OR u.phoneNumber = :username
    """)
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
