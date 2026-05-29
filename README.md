# Advanced Distributed Library Management APIA production-grade, event-driven backend ecosystem built using Spring Boot and Java

This system leverages stateless JWT Authentication to secure REST endpoints and utilizes a decoupled microservice architecture powered by Apache Kafka and Redis to ensure extreme performance and horizontal scalability 
This application is designed using a strictly decoupled, asynchronous architecture to maximize performance and separate resource domains  ──► ( Wi-Fi + JWT Authentication Token )

### System Architecture & Data Flow
 ```
             │
             ▼
  ┌────────────────────────────────────────────────────────┐
  │  Spring Boot Ingest API Engine (Dockerized Micro-API)  │
  ├────────────────────────────────────────────────────────┤
  │  1. JwtAuthenticationFilter validates cryptographic ID  │
  │  2. Route Controller extracts JSON payload parameters   │
  │  3. Async Service fires Kafka Event Pipeline (3ms)     │
  └────────────────────────────────────────────────────────┘
             │
             ▼  (Asynchronous Fire-and-Forget Message Broker)
  ┌────────────────────────────────────────────────────────┐
  │              Apache Kafka Event Cluster                │
  └────────────────────────────────────────────────────────┘
         │                                            │
         ▼ (Background Processing)                    ▼ (Ultra-low Latency Reads)
  ┌─────────────────────────────┐              ┌─────────────────────────────┐
  │ PostgreSQL Database (JDBC)  │              │    Redis In-Memory Cache    │
  │ - Long-term persistent logs │              │ - Telemetry & fast fetches  │
  └─────────────────────────────┘              └─────────────────────────────┘
```

### Tech Stack & ComponentsCore Framework: Spring Boot 3.x 
Security Infrastructure: Stateless JWT (JSON Web Tokens) via JJWT, BCrypt Encryption 
Asynchronous Messaging: Apache Kafka (Event Streaming & Decoupling) 
Performance Cache: Redis (In-Memory RAM caching, <1ms response targets)
Primary Database: PostgreSQL (Relational persistence) 
Virtualization Container: Docker & Docker Compose 
Automated Testing Suite: JUnit 5, Mockito (Unit Testing), Testcontainers (Integration Testing) 

### Project Structure
The project strictly follows the Single Responsibility Principle and Layered Architecture, distributing domain logics across modular components
~~~
├── config/             # Infrastructure Switchboards (SecurityConfig, AppConfig)
├── security/           # Token Cryptography (JwtService, JwtAuthenticationFilter)
├── controller/         # Network Traffic Controllers & HTTP Endpoint Routing
├── service/            # Core Business Brains & Logical Execution Units
├── repository/         # Straight-Line Database Connection Pools (PostgreSQL)
├── entity/             # Permanent Database Structural Blueprints (User, Book)
└── dto/                # Inbound/Outbound Data Carriers (Requests/Responses)
~~~

### Operational Endpoints
###Authentication Context (/auth/**)
- POST /auth/register - Creates a new user profile. Automatically salts passwords using BCrypt and registers standard roles (ROLE_USER). Status: 201 Created.
- POST /auth/login - Cross-examines input credentials against PostgreSQL. Fires a 512-bit signed JWT bearer badge to the client upon successful match 
- Status: 200 OK 

### Inventory Resource Context (/books/**)
- GET /books - Public route. Retrieves full catalog Optimized via Redis @Cacheable annotations 
- POST /books - Secured route. Requires a valid, non-expired JWT Bearer header token. Intercepted globally by OncePerRequestFilter 

### Testing Policy & Design
We enforce strict validation metrics across the Test Pyramid to protect the codebase against behavioral regression 
- Unit Tests: Driven by JUnit 5 and Mockit. Business services are extracted and tested in absolute isolation under 50ms using mocked dependencies to guarantee mathematical determinism 
- Integration Tests: Driven by Testcontainers [index=0.1.11]. Spring context is loaded alongside a transient Docker container instance of a real PostgreSQL database to verify data mapping integrity.
