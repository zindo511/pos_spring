package com.vn.pos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    public Employee employee;

    @Column(name = "total_amount")
    public Double totalAmount;

    @Column(name = "payment_method")
    public String paymentMethod;

    @Column(name = "customer_paid")
    public Double customerPaid;

    @Column(name = "change_amount")
    public Double changeAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
}
