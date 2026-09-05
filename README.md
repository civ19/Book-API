# Book API

A backend system for managing a library book catalog, user accounts, and authenticated access to book management operations.

The API allows users to browse and manage books while providing secure user registration and login through Spring Security and JWT authentication.

The project was built to demonstrate the structure of a real backend application, including REST API design, layered architecture, secure authentication, PostgreSQL persistence, centralized error handling, and automated testing.

---

## What It Does

### 📚 Book Catalog

The API provides functionality for managing a library's book catalog.

Current functionality includes:

* Browse available books
* Create new books
* Retrieve book information
* Update book information
* Delete books
* Manage book resources through REST endpoints

Book creation and other protected operations require authenticated access.

---

## 🔐 User Authentication

Users can register and securely log into the application.

Authentication is implemented using **Spring Security** and **JSON Web Tokens (JWT)**.

The authentication flow is:

```text id="5v5t7e"
Register
   ↓
Password hashed with BCrypt
   ↓
User stored in PostgreSQL
   ↓
Login
   ↓
Spring Security authenticates credentials
   ↓
JWT issued
   ↓
JWT Bearer token used for protected requests
```

Passwords are never stored as plain text. **BCrypt** is used to hash passwords before they are persisted.

The application uses stateless authentication, with Spring Security validating the JWT included with authenticated requests.

---

## 🏗️ Backend Architecture

The application follows a layered architecture that separates the major responsibilities of the backend.

```text id="r2v1zz"
Client
  │
  ▼
Spring Boot REST API
  │
  ▼
Spring Security / JWT
  │
  ▼
Controllers
  │
  ▼
Services / Business Logic
  │
  ▼
Repositories
  │
  ▼
PostgreSQL
```

The project is organized into distinct layers:

```text id="q3t6ak"
config/          Application and security configuration
security/        JWT authentication and security infrastructure
controller/      HTTP request handling and REST endpoints
service/         Business logic
repository/      PostgreSQL data access
entity/          Persistent domain models
dto/             Request and response objects
```

This separation keeps HTTP handling, business logic, persistence, authentication, and data-transfer concerns independent from one another.

---

## 🗄️ Data Persistence

The application uses **PostgreSQL** for persistent storage.

Book and user data are stored in the relational database, with the repository layer responsible for communicating with the database.

This provides persistent application data rather than relying on temporary in-memory storage.

---

## 🛡️ Error Handling

The application uses centralized exception handling to provide consistent responses when errors occur.

Rather than handling every application error individually inside controllers, exceptions are handled through a central mechanism.

This allows cases such as invalid requests, missing resources, and application-level errors to be handled consistently.

---

## 🧪 Testing

Testing is implemented at both the unit and integration levels.

### Unit Testing

**JUnit 5** and **Mockito** are used to test business logic independently from infrastructure dependencies.

Services can be tested using mocked repositories and other dependencies, allowing application behavior to be verified in isolation.

### Integration Testing

**Testcontainers** is used to run PostgreSQL in a temporary Docker container during integration tests.

This allows database interaction and persistence behavior to be tested against a real PostgreSQL instance rather than relying entirely on mocks.

---

## 🚧 Next Release — v1.0.1

The current release focuses on the core backend functionality, authentication, persistence, and testing.

The next release is planned to expand the project's deployment and development workflow.

### 🐳 Docker

A dedicated Dockerfile and Docker Compose configuration will be added to make the application and its database environment easier to build and run consistently.

### 🔄 CI/CD

A CI/CD pipeline will also be introduced to automate the development workflow, including building and testing the application when changes are made.

These features are planned for **v1.0.1** and are not represented as capabilities of the current release.

---

## Technology Stack

| Area                   | Technology             |
| ---------------------- | ---------------------- |
| Language               | Java                   |
| Backend                | Spring Boot 3.x        |
| API                    | REST                   |
| Security               | Spring Security        |
| Authentication         | JWT                    |
| Password Hashing       | BCrypt                 |
| Database               | PostgreSQL             |
| Unit Testing           | JUnit 5, Mockito       |
| Integration Testing    | Testcontainers         |
| Planned Infrastructure | Docker, Docker Compose |
| Planned Automation     | CI/CD                  |

---

## Engineering Highlights

This project demonstrates experience with:

* REST API design
* CRUD operations
* Layered backend architecture
* Spring Boot
* Spring Security
* Stateless JWT authentication
* Secure password handling with BCrypt
* PostgreSQL persistence
* DTO-based API design
* Centralized exception handling
* Unit testing with JUnit 5 and Mockito
* Integration testing with Testcontainers
* Iterative backend development
* Planned containerization and CI/CD automation

---

## Why I Built It

The goal was to build a backend that goes beyond basic database operations.

The library catalog provides the user-facing functionality, while the underlying architecture demonstrates how a backend application handles authentication, business logic, persistence, error handling, and automated testing as a cohesive system.

The project is being developed incrementally, with Docker-based deployment and CI/CD automation planned for the next release.
