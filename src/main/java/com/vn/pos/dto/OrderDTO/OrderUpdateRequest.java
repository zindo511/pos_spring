package com.vn.pos.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {
    private Integer id;
    private Integer employeeId;
    private Double totalAmount;
    private String paymentMethod;
    private Double customerPaid;
    private Double changeAmount;
}
