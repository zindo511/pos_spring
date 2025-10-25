package com.vn.pos.service;

import com.vn.pos.dto.OrderItemDTO.OrderItemRequest;
import com.vn.pos.dto.OrderItemDTO.OrderItemResponse;
import com.vn.pos.dto.OrderItemDTO.OrderItemUpdateRequest;


import java.util.List;

public interface OrderItemService {

    // CRUD
    OrderItemResponse createOrderItem(OrderItemRequest request);
    List<OrderItemResponse> getAllOrderItems();
    OrderItemResponse getOrderItemById(Integer id);
    OrderItemResponse updateOrderItem(OrderItemUpdateRequest request);
    void deleteOrderItemById(Integer id);

    // Link Order
    List<OrderItemResponse> getOrderItemsByOrderId(Integer orderId);
    double getTotalAmountByOrderId(Integer orderId);
    void deleteByOrderId(Integer orderId);

    // Link Product
    List<OrderItemResponse> getOrderItemsByProductId(Integer productId);
    int countOrdersContainingProduct(Integer productId);
    boolean existsByProductName(String productName);

    // Calculation
    double calculateSubTotal(Integer orderItemId);
    OrderItemResponse updateQuantity(Integer orderItemId, Integer newQuantity);
    OrderItemResponse increaseQuantity(Integer orderItemId, Integer amount);
    OrderItemResponse decreaseQuantity(Integer orderItemId, Integer amount);
}
