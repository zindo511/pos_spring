package com.vn.pos.repository;

import com.vn.pos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    List<Order> findOrdersCreatedToday();

    List<Order> getOrdersByEmployee_Id(Integer employeeId);


}
