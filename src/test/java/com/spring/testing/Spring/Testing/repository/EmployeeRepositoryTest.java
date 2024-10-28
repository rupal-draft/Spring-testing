package com.spring.testing.Spring.Testing.repository;

import com.spring.testing.Spring.Testing.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



class EmployeeRepositoryTest extends AbstractImplementationRepository{

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    void setup(){
        employee = Employee
                .builder()
                .id(1L)
                .email("test@gmail.com")
                .name("test")
                .salary(100L)
                .build();
    }

    @Test
    void test_FindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        employeeRepository.save(employee);

        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.getFirst().getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void test_FindByEmail_whenEmailIsNotPresent_thenReturnNoEmployee(){
        String email = "random@gmail.com";
        List<Employee> employeeList = employeeRepository.findByEmail(email);

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }
}