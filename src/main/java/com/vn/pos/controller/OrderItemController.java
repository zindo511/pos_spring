package com.vn.pos.controller;

import com.vn.pos.dto.OrderItemDTO.OrderItemRequest;
import com.vn.pos.dto.OrderItemDTO.OrderItemResponse;
import com.vn.pos.dto.OrderItemDTO.OrderItemUpdateRequest;
import com.vn.pos.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrderItem(@RequestBody OrderItemRequest request){
        try {
            OrderItemResponse response = orderItemService.createOrderItem(request);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItems(){
        List<OrderItemResponse> responses = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Integer id){
        try {
            OrderItemResponse response = orderItemService.getOrderItemById(id);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable Integer id,
            @RequestBody OrderItemUpdateRequest request){
        try {
            request.setId(id);
            OrderItemResponse response = orderItemService.updateOrderItem(request);
            return ResponseEntity.ok(response);
        }catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItemById(@PathVariable Integer id){
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponse>> getOrderItemByOrderId(@PathVariable Integer orderId){
        try {
            List<OrderItemResponse> responses = orderItemService.getOrderItemsByOrderId(orderId);
            return ResponseEntity.ok(responses);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}/total")
    public ResponseEntity<Double> getTotalAmountByOrderId(@PathVariable Integer orderId){
        try {
            double total = orderItemService.getTotalAmountByOrderId(orderId);
            return ResponseEntity.ok(total);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("order/{orderId}")
    public ResponseEntity<Void> deleteByOrderId(@PathVariable Integer orderId){
        try {
            orderItemService.deleteByOrderId(orderId);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByProductId(@PathVariable Integer productId) {
        try {
            List<OrderItemResponse> responses = orderItemService.getOrderItemsByProductId(productId);
            return ResponseEntity.ok(responses);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("product/{productId}/count")
    public ResponseEntity<Integer> countOrdersContainingProduct(@PathVariable Integer productId) {
        try {
            int count = orderItemService.countOrdersContainingProduct(productId);
            return ResponseEntity.ok(count);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("product/exists")
    public ResponseEntity<Boolean> existsByProductName(@RequestParam String productName) {
        boolean exists = orderItemService.existsByProductName(productName);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{orderItemId}/subtotal")
    public ResponseEntity<Double> calculateSubTotal(@PathVariable Integer orderItemId) {
        try {
            double subTotal = orderItemService.calculateSubTotal(orderItemId);
            return ResponseEntity.ok(subTotal);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{orderItemId}/quantity")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Integer orderItemId,
            @RequestParam Integer newQuantity) {
        try {
            orderItemService.updateQuantity(orderItemId, newQuantity);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{orderItemId}/increase")
    public ResponseEntity<Void> increaseQuantity(
            @PathVariable Integer orderItemId,
            @RequestParam Integer amount) {
        try {
            orderItemService.increaseQuantity(orderItemId, amount);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{orderItemId}/decrease")
    public ResponseEntity<Void> decreaseQuantity(
            @PathVariable Integer orderItemId,
            @RequestParam Integer amount) {
        try {
            orderItemService.decreaseQuantity(orderItemId, amount);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
