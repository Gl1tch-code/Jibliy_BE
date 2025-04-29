package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface giftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findAllByUserId(Long userId);
}
