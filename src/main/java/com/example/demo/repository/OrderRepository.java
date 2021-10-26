package com.example.demo.repository;

import com.example.demo.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Modifying
    @Query(value = "INSERT INTO orders(order_id, customer_id) VALUES (:order_id, :user_id)", nativeQuery = true)
    @Transactional
    void save(@Param("order_id")Long orderId, @Param("user_id")Long userId);
}
