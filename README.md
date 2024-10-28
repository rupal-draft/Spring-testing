
# Spring Boot Testing Project

This project demonstrates comprehensive testing techniques within a Spring Boot application. It covers the use of several testing frameworks and libraries, including JUnit, AssertJ, Mockito, Testcontainers, and WebTestClient, to achieve a high level of coverage and robustness across different layers of the application.

## Key Topics Covered

### 1. JUnit Testing Framework
- **JUnit** is the primary testing framework used, providing essential support for unit and integration testing in Java.

### 2. AssertJ Library
- **AssertJ** offers a rich set of assertions for improved readability and maintainability in tests.

### 3. Unit Testing vs Integration Testing
- Understanding the **differences** between unit testing, which focuses on individual components, and integration testing, which tests interactions between components.

### 4. Unit Testing the Persistence Layer
- Configuring unit tests for the persistence layer with a focus on:
  - **Testcontainers**: Using Docker containers for consistent database testing environments.

### 5. Mocking with Mockito
- **Mockito** is used for mocking dependencies, isolating units under test, and verifying interactions.

### 6. Unit Testing the Service Layer
- Writing unit tests for the service layer using Mockito to mock dependencies and verify business logic.

### 7. Integration Testing with Spring Boot
- Setting up integration tests to verify component interactions in a realistic environment.

### 8. Integration Testing the Presentation Layer
- Using **WebTestClient** for integration tests of the controller layer to simulate HTTP requests and validate responses.

## Project Structure

- **Tests** are organized according to the layer they target (e.g., persistence, service, presentation).
- The **`/htmlReport`** directory contains a comprehensive coverage report of the tests.

## Coverage Report

- The full test coverage report is generated in HTML format and located at `/htmlReport`. This report provides insights into test coverage across different modules and classes within the application.

## Getting Started

### Prerequisites
- **Docker** (for Testcontainers)
- **Java 17**
- **Maven** or **Gradle**

### Running Tests
To run the tests, use the following Maven command:

```bash
mvn test
