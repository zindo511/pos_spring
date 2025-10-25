package com.vn.pos.dto.OrderDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Integer employeeId;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private Double totalAmount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Customer paid amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Customer paid must be greater than 0")
    private Double customerPaid;

    private Double changeAmount;
}
