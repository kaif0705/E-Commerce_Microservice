                                                E-COMMERCE MICROSERVICES APPLICATION

This project is a microservices-based e-commerce application demonstrating key patterns and technologies for building scalable, resilient, and maintainable distributed systems with Spring Boot and Spring Cloud.

**About the Project**

This application simulates a basic e-commerce platform with separate microservices for managing orders, products, and users. The architecture is designed to be highly scalable and loosely coupled, with each service having its own domain and data store.

**Core Functionality**
1. User Management: Register, authenticate, and manage user profiles.

2. Product Catalog: Browse and retrieve product details.

3. Order Processing: Create new orders and manage the order lifecycle.

4. Centralized Configuration: All service configurations are managed in a single, version-controlled repository.

5. Service Discovery: Services dynamically register and discover each other without hardcoded addresses.

6. Inter-Service Communication: Services communicate with each other via efficient and load-balanced REST calls.

**Architectural Overview**

### Architectural Overview

The application is built around the following microservices and architectural patterns:

| Service | Role | Key Technologies |
| :--- | :--- | :--- |
| **ConfigServer** | **Centralized Configuration:** Serves configuration from a Git repository to all microservices at startup. | Spring Cloud Config |
| **EurekaServer** | **Service Discovery:** Acts as the service registry, allowing microservices to find each other by name. | Spring Cloud Netflix Eureka |
| **Order** | **Order Processing:** Manages the order domain, communicates with the Product service to check stock. | Spring Boot, Spring Data JPA, PostgreSQL |
| **Product** | **Product Catalog:** Manages product information. | Spring Boot, Spring Data JPA, PostgreSQL |
| **User** | **User Management:** Handles user-related data and authentication. | Spring Boot, Spring Data MongoDB, MongoDB |


**Technologies Used**
1. Framework: Spring Boot 3.5.4
2. Service Discovery: Spring Cloud Netflix Eureka
3. Configuration: Spring Cloud Config
4. Load Balancing: Spring Cloud LoadBalancer
5. Databases: PostgreSQL, MongoDB
6. Messaging: RabbitMQ (for Spring Cloud Bus)
7. Build Tool: Apache Maven
8. Containerization: Docker

