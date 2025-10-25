package com.vn.pos.mapper;

import com.vn.pos.dto.OrderDTO.OrderRequest;
import com.vn.pos.dto.OrderDTO.OrderResponse;
import com.vn.pos.model.Employee;
import com.vn.pos.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    // Request --> Entity (Client --> Database)
    public Order toEntity(OrderRequest request, Employee employee) {
        Order order = new Order();
        order.setEmployee(employee);
        order.setTotalAmount(request.getTotalAmount());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setCustomerPaid(request.getCustomerPaid());
        order.setChangeAmount(request.getChangeAmount());
        return order;
    }

    // Entity --> Response(Database --> Client)
    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setEmployeeId(order.getEmployee() != null ? order.getEmployee().getId() : null);
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setCustomerPaid(order.getCustomerPaid());
        response.setChangeAmount(order.getChangeAmount());
        return response;
    }
}
