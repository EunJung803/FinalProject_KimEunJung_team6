package com.ll.exam.final__2022_10_08.app.order.repository;

import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
