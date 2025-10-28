package com.vn.pos.service.Impl;

import com.vn.pos.exception.Custom.ResourceNotFoundException;
import com.vn.pos.mapper.OrderMapper;
import com.vn.pos.dto.OrderDTO.OrderRequest;
import com.vn.pos.dto.OrderDTO.OrderResponse;
import com.vn.pos.dto.OrderDTO.OrderUpdateRequest;
import com.vn.pos.model.Employee;
import com.vn.pos.model.Order;
import com.vn.pos.repository.EmployeeRepository;
import com.vn.pos.repository.OrderRepository;
import com.vn.pos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee Not Found with: " + request.getEmployeeId()));
        Order order = orderMapper.toEntity(request, employee);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "ID", id));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(OrderUpdateRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getId()));

        Employee employee = request.getEmployeeId() != null ?
               employeeRepository.findById(request.getEmployeeId())
                               .orElseThrow(() -> new RuntimeException("Employee Not Found with: " + request.getEmployeeId()))
                : order.getEmployee();

        order.setEmployee(employee);
        order.setTotalAmount(request.getTotalAmount());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setCustomerPaid(request.getCustomerPaid());
        order.setChangeAmount(request.getChangeAmount());

        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteOrderById(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public double getTotalSalesToday() {
        List<Order> order = orderRepository.findOrdersCreatedToday();
        return order.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }


    @Override
    public List<OrderResponse> getOrdersByEmployeeId(Integer employeeId) {
        List<Order> orders = orderRepository.getOrdersByEmployee_Id(employeeId);
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found with id: " + employeeId);
        }
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

}
