package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.FileEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query("SELECT fe FROM FileEntity fe WHERE fe.isBanner = true")
    Optional<List<FileEntity>> findAllByBanner();
}
