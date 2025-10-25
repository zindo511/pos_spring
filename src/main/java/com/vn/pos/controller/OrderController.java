package com.vn.pos.controller;

import com.vn.pos.dto.OrderDTO.OrderRequest;
import com.vn.pos.dto.OrderDTO.OrderResponse;
import com.vn.pos.dto.OrderDTO.OrderUpdateRequest;
import com.vn.pos.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Integer id,
            @RequestBody OrderUpdateRequest request) {
        request.setId(id);
        OrderResponse response = orderService.updateOrder(request);
        return  ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Integer id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics/total-today")
    public ResponseEntity<Double> getTotalSalesToday() {
        Double total = orderService.getTotalSalesToday();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByEmployeeId(@PathVariable Integer employeeId) {
        List<OrderResponse> responses = orderService.getOrdersByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

}
