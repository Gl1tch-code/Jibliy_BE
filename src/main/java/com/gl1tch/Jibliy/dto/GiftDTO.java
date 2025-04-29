package com.gl1tch.Jibliy.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class GiftDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
