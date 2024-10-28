package com.spring.testing.Spring.Testing.controller;

import com.spring.testing.Spring.Testing.dto.EmployeeDto;
import com.spring.testing.Spring.Testing.entities.Employee;
import com.spring.testing.Spring.Testing.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class EmployeeControllerTestIT extends AbstractIntegrationController{

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeDto employeeDto;
    private Employee employee;
    private Employee updatedEmployee;
    private EmployeeDto updatedEmployeeDto;

    @BeforeEach
    void setup(){
        employee = Employee.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("Test")
                .salary(200L)
                .build();
        updatedEmployee = Employee.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("Random")
                .salary(300L)
                .build();
        employeeDto = modelMapper.map(employee,EmployeeDto.class);
        updatedEmployeeDto = modelMapper.map(updatedEmployee,EmployeeDto.class);
        employeeRepository.deleteAll();
    }


    @Test
    void testGetEmployeeById_Success() {
        Employee savedEmployee = employeeRepository.save(employee);
        webTestClient
                .get()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .value((employee)->{
                    Assertions.assertThat(employee.getEmail()).isEqualTo(employeeDto.getEmail());
                    Assertions.assertThat(employee.getName()).isEqualTo(employeeDto.getName());
                    Assertions.assertThat(employee.getSalary()).isEqualTo(employeeDto.getSalary());
                });
    }
    @Test
    void testGetEmployeeById_Notfound(){
        webTestClient
                .get()
                .uri("/employees/2")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testCreateNewEmployee_SameEmail_throwsServerError() {
        employeeRepository.save(employee);
        webTestClient
                .post()
                .uri("/employees")
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
    @Test
    void testCreateNewEmployee_Success(){
        webTestClient
                .post()
                .uri("/employees")
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(EmployeeDto.class)
                .value((employee)->{
                    Assertions.assertThat(employee.getEmail()).isEqualTo(employeeDto.getEmail());
                    Assertions.assertThat(employee.getName()).isEqualTo(employeeDto.getName());
                    Assertions.assertThat(employee.getSalary()).isEqualTo(employeeDto.getSalary());
                });
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee savedEmployee = employeeRepository.save(employee);
        webTestClient
                .put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(updatedEmployee)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.email")
                    .isEqualTo(updatedEmployeeDto.getEmail())
                .jsonPath("$.name")
                    .isEqualTo(updatedEmployeeDto.getName())
                .jsonPath("$.salary")
                    .isEqualTo(updatedEmployeeDto.getSalary());
    }
    @Test
    void testUpdateEmployee_WhenEmployeeDoesNotExist() {
        webTestClient
                .put()
                .uri("/employees/999")
                .bodyValue(updatedEmployee)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
    @Test
    void testUpdateEmployee_WhenAttemptingWithSameEmail() {
        Employee savedEmployee = employeeRepository.save(employee);
        updatedEmployee.setEmail("random@gmail.com");
        webTestClient
                .put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(updatedEmployee)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void testDeleteEmployee_whenEmployeePresent() {
        Employee savedEmployee = employeeRepository.save(employee);
        webTestClient
                .delete()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
        webTestClient
                .delete()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus()
                .isNotFound();
    }
    @Test
    void testDeleteEmployee_whenEmployeeNotFound() {
        webTestClient
                .delete()
                .uri("/employees/99")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}