package com.vn.pos.service.Impl;

import com.vn.pos.mapper.OrderItemMapper;
import com.vn.pos.dto.OrderItemDTO.OrderItemRequest;
import com.vn.pos.dto.OrderItemDTO.OrderItemResponse;
import com.vn.pos.dto.OrderItemDTO.OrderItemUpdateRequest;
import com.vn.pos.model.Order;
import com.vn.pos.model.OrderItem;
import com.vn.pos.model.Product;
import com.vn.pos.repository.OrderItemRepository;
import com.vn.pos.repository.OrderRepository;
import com.vn.pos.repository.ProductRepository;
import com.vn.pos.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemResponse createOrderItem(OrderItemRequest request) {
        validateRequest(request);
        if (orderItemRepository.existsByProductName(request.getProductName())) {
            throw new RuntimeException("Order Item already exists");
        }

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found with: " + request.getOrderId()));
        Product product = productRepository.findById(request.productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found with: " + request.productId));

        OrderItem orderItem = orderItemMapper.toEntity(request, order, product);
        OrderItem saved = orderItemRepository.save(orderItem);

        return orderItemMapper.toResponse(saved);
    }

    @Override
    public List<OrderItemResponse> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse getOrderItemById(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + id));
        return orderItemMapper.toResponse(orderItem);
    }

    @Override
    @Transactional
    public OrderItemResponse updateOrderItem(OrderItemUpdateRequest orderItem) {
        validateUpdateRequest(orderItem);

        OrderItem existing = orderItemRepository.findById(orderItem.getId())
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + orderItem.getId()));

        Order order = orderItem.getOrderId() != null ?
                orderRepository.findById(orderItem.getOrderId())
                        .orElseThrow(() -> new RuntimeException("Order Not Found with: " + orderItem.getOrderId()))
                : existing.getOrder();

        Product product = orderItem.getProductId() != null ?
                productRepository.findById(orderItem.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product Not Found with: " + orderItem.getProductId()))
                : existing.getProduct();

        existing.setOrder(order);
        existing.setProduct(product);
        existing.setProductName(existing.getProductName());
        existing.setQuantity(orderItem.getQuantity());
        existing.setPrice(orderItem.getPrice());
        existing.setSubtotal(orderItem.getPrice()*orderItem.getQuantity());

        OrderItem saved = orderItemRepository.save(existing);
        return orderItemMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteOrderItemById(Integer id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found with: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItemResponse> getOrderItemsByOrderId(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(orderId);

        if (orderItems.isEmpty()) {
            throw new RuntimeException("OrderItem not found with: " + orderId);
        }
        return orderItems.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalAmountByOrderId(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(orderId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("OrderItem not found with: " + orderId);
        }

        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    @Transactional
    public void deleteByOrderId(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(orderId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("OrderItem not found with: " + orderId);
        }
        orderItemRepository.deleteByOrder_Id(orderId);
    }

    @Override
    public List<OrderItemResponse> getOrderItemsByProductId(Integer productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProduct_Id(productId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("OrderItem not found with: " + productId);
        }
        return orderItems.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public int countOrdersContainingProduct(Integer productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProduct_Id(productId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("OrderItem not found with: " + productId);
        }
        return orderItems.size();
    }

    @Override
    public boolean existsByProductName(String productName) {
        return orderItemRepository.existsByProductName(productName);
    }

    @Override
    public double calculateSubTotal(Integer orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + orderItemId));
        return orderItem.getPrice() * orderItem.getQuantity();
    }

    @Override
    @Transactional
    public OrderItemResponse updateQuantity(Integer orderItemId, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + orderItemId));
        orderItem.setQuantity(newQuantity);
        orderItem.setSubtotal(orderItem.getPrice() * newQuantity);
        OrderItem updated = orderItemRepository.save(orderItem);

        return orderItemMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public OrderItemResponse increaseQuantity(Integer orderItemId, Integer amount) {
        if (amount <= 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + orderItemId));
        orderItem.setQuantity(orderItem.getQuantity() + amount);
        OrderItem updated = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public OrderItemResponse decreaseQuantity(Integer orderItemId, Integer amount) {
        if (amount <= 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with: " + orderItemId));

        int newQuantity = orderItem.getQuantity() - amount;
        if (newQuantity < 0) {
            throw new RuntimeException("Can't decrease quantity. Result would be negative. Current: "
                + orderItem.getQuantity() + " trying to decrease: " + amount);
        }
        orderItem.setQuantity(newQuantity);
        OrderItem updated = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(updated);
    }

    public void validateRequest(OrderItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            throw new RuntimeException("Quantity required");
        }
        if (request.getPrice() == null || request.getPrice() < 1) {
            throw new RuntimeException("Price required");
        }
    }

    public void validateUpdateRequest(OrderItemUpdateRequest request) {
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            throw new RuntimeException("Quantity required");
        }
        if (request.getPrice() == null || request.getPrice() < 1) {
            throw new RuntimeException("Price required");
        }
    }
}
