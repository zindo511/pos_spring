package com.vn.pos.repository;

import com.vn.pos.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_Id(Integer orderId);

    void deleteByOrder_Id(Integer orderId);

    List<OrderItem> findByProduct_Id(Integer productId);

    boolean existsByProductName(String productName);
}
