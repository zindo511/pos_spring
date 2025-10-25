package com.vn.pos.mapper;

import com.vn.pos.dto.OrderItemDTO.OrderItemRequest;
import com.vn.pos.dto.OrderItemDTO.OrderItemResponse;
import com.vn.pos.model.Order;
import com.vn.pos.model.OrderItem;
import com.vn.pos.model.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    // Request --> Entity (Client --> Database)
    public OrderItem toEntity(OrderItemRequest request, Order order, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductName(request.getProductName());
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(request.getPrice());
        orderItem.setSubtotal(request.getPrice()*request.getQuantity());

        return orderItem;
    }

    // Entity --> Response(Database --> Client)
    public OrderItemResponse toResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setOrderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null);
        response.setProductId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null);
        response.setProductName(orderItem.getProductName());
        response.setQuantity(orderItem.getQuantity());
        response.setPrice(orderItem.getPrice());
        response.setSubtotal(orderItem.getSubtotal());
        return response;
    }
}
