package com.gl1tch.Jibliy.dto;

import com.gl1tch.Jibliy.utils.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDTO {
    public Long id;
    public OrderStatusEnum status;
    public BigDecimal totalPrice;
}
