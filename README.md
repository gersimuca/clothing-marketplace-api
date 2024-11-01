Here's a `README.md` template for your **Second-Hand Clothes Marketplace API**. This file includes installation, setup, usage instructions, API endpoint descriptions, and other essential information.

---

# Second-Hand Clothes Marketplace API

This project is a REST API for a second-hand clothes marketplace, allowing users to publish, sell, and manage second-hand garments. Built with Java and Spring Boot, it supports authentication, publishing, and managing garment listings with JWT-secured endpoints.

## Table of Contents
1. [Features](#features)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Getting Started](#getting-started)
5. [API Endpoints](#api-endpoints)
6. [Running Tests](#running-tests)
7. [License](#license)

---

## Features

- User authentication with JWT
- Custom user model with publisher information (id, full name, address)
- CRUD operations for garment listings
- Query and filter garments by type, size, and price
- Secure publishing endpoints accessible only to authenticated users
- Users can only manage (update/unpublish) their own garments

## Technologies

- **Java** (v21 or later)
- **Spring Boot** (v3.3.3+)
- **Spring Data JPA** (for database interactions)
- **Spring Security + JWT** (for authentication)
- **Maven** (for dependency management and build)

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/gersimuca/second-hand-clothes-api.git
    cd clothing-marketplace-api
    ```

2. **Configure Database**:
   Update your database settings in `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/second_hand_clothes
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    ```

3. **Install Dependencies**:
    ```bash
    mvn install
    ```

## Getting Started

1. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

2. **Access API**:
   The API will be available at `http://localhost:8080`.

## API Endpoints

### Authentication
- **POST /auth/login**
    - Description: Authenticates a user and returns a JWT token.
    - Request Body: `{ "username": "user", "password": "pass" }`
    - Response: `{ "token": "jwt-token" }`

### Public Endpoints (No Authentication Required)

- **GET /clothes**
    - Description: Retrieves a list of all published garments. Supports query parameters for filtering by `type`, `size`, and `price`.
    - Example: `/clothes?type=shirt&size=M`

- **GET /clothes/{id}**
    - Description: Retrieves details for a specific garment by ID.

### Authenticated Endpoints (JWT Required)

- **POST /clothes**
    - Description: Allows an authenticated user to publish a new garment listing.
    - Request Body:
      ```json
      {
        "type": "shirt",
        "description": "A cozy flannel shirt",
        "size": "M",
        "price": 20.00
      }
      ```
    - Response: `{ "id": 1, "type": "shirt", ... }`

- **PUT /clothes/{id}**
    - Description: Allows an authenticated user to update an existing garment listing they own.
    - Request Body:
      ```json
      {
        "type": "jacket",
        "description": "A winter coat",
        "size": "L",
        "price": 50.00
      }
      ```

- **DELETE /clothes/{id}**
    - Description: Allows an authenticated user to unpublish (delete) a garment listing they own.

## Running Tests

1. **Run all tests** (if provided):
    ```bash
    mvn test
    ```

## License

This project is licensed under the MIT License.

---