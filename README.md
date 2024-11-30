# E-Commerce Secured Web Service

## Description

This project is a fully functional **e-commerce secured web service** built using **Spring Boot**. It provides a robust backend RESTful API for managing core e-commerce functionalities such as **products**, **orders**, and **cart management**. The application implements secure **user authentication** using **JWT (JSON Web Tokens)** and **method-based authorization** with **roles and authorities**.

---

## Features

### Core Functionalities

1. **Products**:
   - Add, update, delete, and fetch products.
   - Each product supports a feature to associate and retrieve an image.

2. **Cart Management**:
   - Add and remove items from the cart.
   - View cart details and checkout functionality.

3. **Order Management**:
   - Place orders for items in the cart.
   - View past orders and order statuses.

### Authentication and Authorization

1. **User Authentication**:
   - Implemented using JWT for secure and stateless authentication.
   - Secure login and registration endpoints.

2. **Roles and Authorities**:
   - Method-based authorization using roles like `ADMIN`, `USER`, etc.
   - Fine-grained access control using Spring Security.

### Additional Features

1. **Image Management**:
   - Product images are saved as files within the project structure.
   - Image retrieval via RESTful endpoints.

2. **Auditing and Base Entity**:
   - Track `createdBy`, `createdDate`, `modifiedBy`, and `modifiedDate` for all entities.
   - Common attributes like `id` defined in a shared **BaseEntity**.


3. **Functional Programming**:
   - Java Streams are utilized for clean and efficient data processing.

4. **DTOs (Data Transfer Objects)**:
   - DTOs ensure clear separation between the data layer and API responses.

---

## Technology Stack

| **Technology**           | **Purpose**                                |
|--------------------------|--------------------------------------------|
| **Java** (v17 or above)   | Backend programming language.              |
| **Spring Boot**           | Framework for building the application.    |
| **Spring Security**       | Authentication and authorization.          |
| **Spring Data JPA**       | ORM for database interactions.             |
| **Lombok**                | Simplifies boilerplate code like getters, setters, etc. |
| **JWT**                   | Stateless user authentication.             |
| **H2/SQL Database**       | Database for persistent data storage.      |
| **Functional Programming**| Java Streams for efficient data processing. |
| **Maven**                 | Dependency management.                     |

