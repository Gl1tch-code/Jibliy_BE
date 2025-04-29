package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.Order;
import com.gl1tch.Jibliy.utils.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.email = :email")
    List<Order> findAllByUserEmail(String email);

    @Query("SELECT o FROM Order o ORDER BY o.id")
    List<Order> findAll();

    @Query("SELECT count(o) FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    Integer countByUserIdAndStatus(Long userId, OrderStatusEnum status);
}
