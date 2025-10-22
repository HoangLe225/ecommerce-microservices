# 🛍️ E-commerce Microservices Project

This project is a simple **e-commerce system** built using the **Microservices** architecture to handle a basic **product ordering** process.

---

## 💡 Architecture Overview

The system is divided into independent services that communicate with each other via **REST APIs**. All services are centrally managed and configured to ensure flexibility and scalability.

---

## 🛠️ Technologies & Key Components

The project uses the **Spring Cloud** stack along with **Java 17** to build the microservices.

| Component | Technology / Purpose | Details |
| :--- | :--- | :--- |
| **Backend Framework** | **Spring Boot** (Java 17) | Builds independent microservices. |
| **Database** | **MySQL** | Relational database for the services. |
| **Service Discovery** | **Eureka Server** | Allows services to register and discover each other. |
| **Configuration** | **Spring Cloud Config Server** | Centralized configuration management for all services. |
| **API Gateway** | **Spring Cloud Gateway** | Single entry point for all client requests; handles routing and security. |
| **Giao tiếp Inter-Service** | **Feign Client** | Synchronous communication between services using RESTful APIs. |
| **Security** | **JWT** (JSON Web Token) | User authentication and authorization. |
| **API Communication** | **REST API** | Main communication method between services and from Gateway to Client. |
| **Fault Tolerance** | **Resilience4j** (Circuit Breaker) | Implements **Circuit Breaker** to improve system stability and resilience. |

---

## ✅ Core Features

| Service | Endpoint(s) | Description |
| :--- | :--- | :--- |
| **User Service** | `/api/users/register`, `/api/users/login` | User registration and login, **JWT creation/validation**. |
| **Product Service** | `/api/products`, `/api/products/{id}` | **CRUD** operations for products. |
| **Order Service** | `/api/orders` (POST), `/api/orders/{id}`, `/api/orders/{id}/order_items` | **Create and retrieve orders**; connects with User and Product services. |

---

## 🧩 Microservices Overview

The project consists of three core microservices for the order processing flow:

1.  **User Service**
    * Manages user information (registration, login).
    * Provides authentication-related APIs (**JWT** generation/validation).

2.  **Product Service**
    * Manages product data (Create, Read, Update, Delete).
    * Manages product inventory (stock quantity).

3.  **Order Service**
    * Handles order logic (creating new orders).
    * Uses **Feign Client** to call **Product Service** (check and deduct stock) and **User Service** (retrieve user details).
    * Applies **Circuit Breaker** (**Resilience4j**) when calling other services.

---

## 🚀 Workflow (Ordering Process)

1.  The **client** sends an order request to the **API Gateway**.
2.  The **API Gateway**, after validating the **JWT**, routes the request to the **Order Service**.
3.  **Order Service** uses **Feign Client** to:
    * Calls the **User Service** to retrieve user details.
    * Calls the **Product Service** to check and deduct stock.
    * If the **Product Service** is unavailable, **Resilience4j's Circuit Breaker** activates and returns a **fallback** response.
4.  After confirmation, the **Order Service** saves the order to the database and returns the result to the client via the **API Gateway**.

---

## ⚙️ Project Setup & Run Instructions

### System Requirements

* **Java 17** or higher.
* **Maven** 3.x.
* **MySQL** Database.

### Folder Structure
```
ecommerce-microservices/ 
├── config-server/ 
├── eureka-server/ 
├── api-gateway/ 
├── user-service/ 
├── product-service/ 
├── order-service/ 
└── README.md
```
### Database Configuration
Create the necessary schemas for each service:: `users_db`, `products_db`, `orders_db`.

### Deployment Steps

1.  **Start Config Server & Eureka Server:**
    * Clone the `config-server` and `eureka-server` repositories.
    * Run both servers so that other services can locate configs and register themselves.
2.  **Initialize Databases:**
    * Create required MySQL schemas for the User, Product, and Order services.
    * Configure DB connection settings in `config-server` or local `application.yml` files.
3.  **Start Microservices:**
    * Start `user-service`, `product-service`, and `order-service`.
4.  **Start API Gateway:**
    * Launch the `api-gateway` to start receiving client requests.

### Default Ports

| Service | Port |
| :--- | :--- |
| **Eureka Server** | `8761` |
| **Config Server** | `8888` |
| **API Gateway** | `8080` |
| **Product Service** | `8081` |
| **User Service** | `8082` |
| **Order Service** | `8083` |

---

## 📌 Development Notes
* The project uses Spring Boot 3+ (compatible with Java 17) and Spring Cloud 2022+.
* To test the system easily, consider using a Postman collection (if available) to call the APIs via the API Gateway: http://localhost:8080/.
---

## 🤝 Contributions

All contributions (pull requests) are welcome. Please create an issue first if you'd like to suggest a major change.

---

## 📝 License

N/A.
