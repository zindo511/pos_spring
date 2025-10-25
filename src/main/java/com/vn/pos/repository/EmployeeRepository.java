package com.vn.pos.repository;

import com.vn.pos.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);
    boolean existsByUsername(String username);
    List<Employee> findByRole(Employee.EmployeeRole role);
}
