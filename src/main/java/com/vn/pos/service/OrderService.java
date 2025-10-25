package com.vn.pos.service;

import com.vn.pos.dto.OrderDTO.OrderRequest;
import com.vn.pos.dto.OrderDTO.OrderResponse;
import com.vn.pos.dto.OrderDTO.OrderUpdateRequest;
import com.vn.pos.model.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Integer id);
    OrderResponse updateOrder(OrderUpdateRequest request);
    void deleteOrderById(Integer id);

    double getTotalSalesToday();
    List<OrderResponse> getOrdersByEmployeeId(Integer employeeId);
}
