package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
