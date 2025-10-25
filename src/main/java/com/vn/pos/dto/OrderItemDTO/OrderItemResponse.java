package com.vn.pos.dto.OrderItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Integer id;
    public Integer orderId;
    public Integer productId;
    public String productName;
    public Integer quantity;
    public Double price;
    public Double subtotal;
}
