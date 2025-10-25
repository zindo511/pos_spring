package com.vn.pos.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer employeeId;
    private Double totalAmount;
    private String paymentMethod;
    private Double customerPaid;
    private Double changeAmount;
    private Date createdAt;
}
