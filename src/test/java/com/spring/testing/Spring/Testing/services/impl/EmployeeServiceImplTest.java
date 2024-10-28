package com.spring.testing.Spring.Testing.services.impl;
import com.spring.testing.Spring.Testing.dto.EmployeeDto;
import com.spring.testing.Spring.Testing.entities.Employee;
import com.spring.testing.Spring.Testing.exception.ResourceNotFoundException;
import com.spring.testing.Spring.Testing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest extends AbstractServiceImplementation{

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("Test")
                .salary(200L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_whenEmployeePresent_getEmployeeDto() {
        when(employeeRepository
                .findById(mockEmployee.getId()))
                .thenReturn(Optional.of(mockEmployee));
        EmployeeDto employeeDto = employeeService
                .getEmployeeById(mockEmployee.getId());
        assertThat(employeeDto)
                .isNotNull();
        assertThat(employeeDto.getId())
                .isEqualTo(mockEmployee.getId());
        assertThat(employeeDto.getEmail())
                .isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository,only())
                .findById(mockEmployee.getId());
    }

    @Test
    void testGetEmployeeById_whenEmployeeNotPresent_throwException(){
        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()->employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
    }

    @Test
    void testCreateNewEmployee_whenEmployeeEmailIsNotPresent() {
        when(employeeRepository.findByEmail(mockEmployee.getEmail())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        assertThat(employeeDto.getId()).isEqualTo(mockEmployee.getId());
        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee employee = argumentCaptor.getValue();
        assertThat(employee.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        assertThat(employee.getId()).isEqualTo(mockEmployeeDto.getId());
    }

    @Test
    void testCreateNewEmployee_whenEmployeeEmailIsNotPresent_throwsException(){
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail()))
                .thenReturn(List.of(mockEmployee));
        assertThatThrownBy(()->employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: " + mockEmployeeDto.getEmail());
    }

    @Test
    void testUpdateEmployee_whenValid_updateEmployee() {
        when(employeeRepository.findById(mockEmployeeDto.getId()))
                .thenReturn(Optional.of(mockEmployee));

        mockEmployeeDto.setName("random name");
        mockEmployeeDto.setSalary(300L);

        Employee newEmployee = modelMapper.map(mockEmployeeDto,Employee.class);
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(newEmployee);

        EmployeeDto employeeDto = employeeService
                .updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto);
        assertThat(employeeDto).isEqualTo(mockEmployeeDto);
    }

    @Test
    void updateEmployee_whenEmployeeNotPresent_throwsException(){
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()->employeeService.updateEmployee(1L,mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + 1);
    }

    @Test
    void updateEmployee_attemptingToUpdateEmployeeEmail_throwsException(){
        when(employeeRepository.findById(mockEmployeeDto.getId()))
                .thenReturn(Optional.of(mockEmployee));

        mockEmployeeDto.setName("Random");
        mockEmployeeDto.setEmail("random@gmail.com");

        assertThatThrownBy(()->employeeService.updateEmployee(1L,mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");
    }

    @Test
    void testDeleteEmployee_whenValidEmployee_deleteEmployee() {
        when(employeeRepository.existsById(1L))
                .thenReturn(true);
        assertThatCode(()->employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_whenEmployeeNotPresent_throwsException(){
        when(employeeRepository.existsById(anyLong()))
                .thenReturn(false);

        assertThatThrownBy(()->employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
    }
}