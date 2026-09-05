# Advanced Library Management API

A production-oriented REST API built with **Java and Spring Boot**, providing authenticated user access and CRUD operations for a library book catalog.

The application uses **Spring Security with stateless JWT authentication**, PostgreSQL for persistent storage, Redis for caching, and Docker for reproducible development and deployment.

## Architecture

```text
                   HTTP Client
                       │
                       ▼
              ┌─────────────────┐
              │  Spring Boot API │
              └────────┬────────┘
                       │
              ┌────────▼────────┐
              │ Spring Security │
              │ JWT Validation  │
              └────────┬────────┘
                       │
                       ▼
                ┌─────────────┐
                │ Controllers │
                └──────┬──────┘
                       │
                       ▼
                 ┌───────────┐
                 │ Services  │
                 └─────┬─────┘
                       │
              ┌────────┴─────────┐
              ▼                  ▼
        ┌───────────┐      ┌───────────┐
        │ PostgreSQL│      │   Redis   │
        │ Persistent│      │  Caching  │
        │  Storage  │      │           │
        └───────────┘      └───────────┘
```

## Core Technologies

* **Java**
* **Spring Boot 3.x**
* **Spring Security**
* **JWT authentication**
* **BCrypt password hashing**
* **PostgreSQL**
* **Redis**
* **Docker / Docker Compose**
* **JUnit 5**
* **Mockito**
* **Testcontainers**

## Architecture & Design

The application follows a layered architecture with clear separation of responsibilities:

```text
config/          Application and security configuration
security/        JWT authentication and security infrastructure
controller/      HTTP request handling and REST endpoints
service/         Business logic
repository/      PostgreSQL data access
entity/          Persistent domain models
dto/             Request and response objects
```

The API separates HTTP handling, business logic, persistence, authentication, and data-transfer concerns rather than placing application logic directly inside controllers.

## Authentication

Authentication is implemented using **Spring Security and stateless JWTs**.

### `POST /auth/register`

Creates a new user account.

* Passwords are hashed using BCrypt.
* New users receive the standard user role.
* Returns an appropriate HTTP success status.

### `POST /auth/login`

Authenticates a user's credentials and returns a signed JWT upon successful authentication.

Subsequent protected requests authenticate using the JWT Bearer token.

Spring Security's request filtering infrastructure validates the token before allowing access to protected endpoints.

## Book API

### `GET /books`

Retrieves the available book catalog.

The endpoint uses Redis caching to reduce repeated database reads.

### `POST /books`

Creates a new book.

The endpoint requires successful JWT authentication.

Additional CRUD operations are implemented for managing book resources.

## Testing

The project uses multiple levels of automated testing.

### Unit Testing

**JUnit 5 and Mockito** are used to test business logic independently from infrastructure dependencies.

Services can be tested with mocked repositories and other dependencies, allowing business behavior to be verified in isolation.

### Integration Testing

**Testcontainers** is used to run PostgreSQL in a temporary Docker container during integration tests.

This allows database interaction and persistence behavior to be tested against a real PostgreSQL instance rather than relying exclusively on mocks.

## Deployment

The application and its supporting infrastructure can be run using **Docker Compose**, providing a reproducible local environment for the Spring Boot application, PostgreSQL, and Redis.

## Project Goals

The project demonstrates practical experience with:

* REST API design
* Spring Boot application architecture
* Spring Security
* Stateless authentication
* JWT-based authorization
* Relational database persistence
* Redis caching
* Layered architecture
* Automated unit and integration testing
* Containerized development
