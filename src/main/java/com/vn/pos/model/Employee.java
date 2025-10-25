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
@Table(name = "employees")

public class Employee extends AbstractEntity {

    public String username;
    public String password;

    @Column(name = "full_name")
    public String fullName;

    public enum EmployeeRole {
        ADMIN,
        CASHIER
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EmployeeRole role;

    public String phone;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
