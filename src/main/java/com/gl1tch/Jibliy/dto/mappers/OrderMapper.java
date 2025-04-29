package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.Order;
import com.gl1tch.Jibliy.dto.OrderDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(Order order);
    List<OrderDTO> toListOrderDTO(List<Order> orders);
}
