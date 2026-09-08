# 📡 Telecom Service Provisioning Platform

**TelcoFlow** is an event-driven Telecom Service Provisioning Platform built using **Java, Spring Boot, Spring Cloud, Apache Kafka, PostgreSQL, and Docker**.

The platform automates telecom workflows such as **customer onboarding, SIM activation, service requests, order processing, provisioning, and customer notifications** using a scalable **microservices architecture**.

---

## 🚀 Problem Statement

Telecom service operations often involve multiple independent processes such as customer registration, service requests, order management, service provisioning, and notifications.

In a traditional monolithic application, these responsibilities can become tightly coupled, making the system difficult to:

* Scale independently
* Maintain and deploy
* Handle failures
* Process asynchronous operations
* Add new telecom services

To address these challenges, **TelcoFlow** separates the major business capabilities into independent microservices.

Each service is responsible for a specific business domain and communicates with other services through REST APIs and event-driven messaging.

---

## 🏗️ Architecture

```text
                         ┌───────────────────┐
                         │      Client       │
                         └─────────┬─────────┘
                                   │
                                   ▼
                         ┌───────────────────┐
                         │    API Gateway    │
                         │ Spring Cloud GW   │
                         │  JWT Security     │
                         └─────────┬─────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
              ▼                    ▼                    ▼
      ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
      │ Auth Service │     │   Customer   │     │    Order     │
      │              │     │   Service    │     │   Service    │
      └──────────────┘     └──────────────┘     └──────┬───────┘
                                                        │
                                                        ▼
                                               ┌──────────────────┐
                                               │   Provisioning   │
                                               │     Service      │
                                               └────────┬─────────┘
                                                        │
                                                        ▼
                                               ┌──────────────────┐
                                               │   Notification   │
                                               │     Service      │
                                               └──────────────────┘

                         ┌───────────────────┐
                         │      Eureka       │
                         │  Service Registry │
                         └───────────────────┘

                         ┌───────────────────┐
                         │      Kafka        │
                         │ Event Messaging   │
                         └───────────────────┘

                         ┌───────────────────┐
                         │    PostgreSQL     │
                         │     Database      │
                         └───────────────────┘
```

---

## 🧩 Microservices

| Service                  | Responsibility                                             |
| ------------------------ | ---------------------------------------------------------- |
| **API Gateway**          | Central entry point, routing and authentication            |
| **Auth Service**         | User registration, login, JWT and refresh-token management |
| **Customer Service**     | Customer onboarding and customer information               |
| **Order Service**        | Service request and order management                       |
| **Provisioning Service** | Telecom service provisioning workflows                     |
| **Notification Service** | Customer notifications                                     |
| **Discovery Server**     | Service registration and discovery using Eureka            |

---

## 🔄 Example Workflow

### New SIM Activation

A typical workflow looks like:

```text
Customer
   │
   ▼
API Gateway
   │
   ▼
Customer Service
   │
   ▼
Order Service
   │
   ▼
Kafka Event
   │
   ▼
Provisioning Service
   │
   ▼
Service Activation
   │
   ▼
Kafka Event
   │
   ▼
Notification Service
   │
   ▼
Customer Notification
```

This allows long-running or independent operations to be processed asynchronously.

---

## 🔐 Authentication & Security

The platform uses **JWT-based authentication** through the API Gateway.

### Authentication Flow

```text
Login
  │
  ▼
Auth Service
  │
  ├── Access Token
  │
  └── Refresh Token
          │
          ▼
       PostgreSQL
```

The API Gateway validates the access token before forwarding requests to downstream services.

### Refresh Token

Refresh tokens are managed separately from access tokens, allowing the client to obtain a new access token without requiring the user to log in again.

The platform also supports refresh-token rotation, where the previous refresh token can be revoked when a new refresh token is issued.

---

## 📨 Event-Driven Communication

**Apache Kafka** is used for asynchronous communication between services.

For example:

```text
Order Created
     │
     ▼
 Kafka Event
     │
     ▼
Provisioning Service
     │
     ▼
Provisioning Completed
     │
     ▼
 Kafka Event
     │
     ▼
Notification Service
```

### Benefits

* Loose coupling between services
* Asynchronous processing
* Better scalability
* Independent service development
* Improved resilience

---

## 🔎 Service Discovery

The project uses **Netflix Eureka** for service discovery.

Instead of hardcoding service URLs:

```text
http://localhost:8081
```

services can communicate using service names through the service registry.

```text
CUSTOMER-SERVICE
ORDER-SERVICE
PROVISIONING-SERVICE
NOTIFICATION-SERVICE
```

This makes the architecture easier to scale and deploy.

---

## 🛠️ Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Spring Cloud Netflix Eureka
* Spring Data JPA
* OpenFeign
* Apache Kafka

### Database

* PostgreSQL

### Security

* JWT
* Access Tokens
* Refresh Tokens
* Refresh Token Rotation
* HttpOnly Cookies

### API Documentation

* Swagger / OpenAPI

### DevOps

* Docker
* Docker Compose

---

## 📁 Project Structure

```text
Telecom-Service-Provisioning-Platform/
│
├── api-gateway/
│
├── auth-service/
│
├── customer-service/
│
├── discovery-server/
│
├── notification-service/
│
├── order-service/
│
├── provisioning-service/
│
├── docker-compose.yml
│
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure the following are installed:

* Java 17+
* Maven
* Docker
* Docker Compose
* PostgreSQL
* Apache Kafka

---

### Clone the Repository

```bash
git clone https://github.com/mdswaley/Telecom-Service-Provisioning-Platform.git
```

```bash
cd Telecom-Service-Provisioning-Platform
```

---

### Run with Docker Compose

```bash
docker-compose up --build
```

This starts the required infrastructure and services defined in the project.

---

## 📚 API Documentation

Each microservice exposes its API documentation using **Swagger/OpenAPI**.

The API Gateway provides a centralized entry point for accessing the APIs.

Example:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 API Request Flow

For a protected API:

```text
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
API Gateway
  │
  ├── Validate JWT
  │
  ├── Validate expiration
  │
  └── Forward request
          │
          ▼
    Microservice
```

Requests without a valid JWT are rejected at the gateway.

---

## 🎯 Key Design Goals

The project focuses on:

* Microservices architecture
* Service independence
* Event-driven communication
* Secure API access
* Service discovery
* Asynchronous processing
* Scalable telecom workflows
* Containerized deployment

---

## 🔮 Future Enhancements

Potential improvements include:

* Redis-based token/session management
* Circuit Breaker using Resilience4j
* Distributed tracing
* Centralized configuration
* Prometheus and Grafana monitoring
* ELK-based centralized logging
* Kubernetes deployment
* SMS and WhatsApp notifications
* Advanced order-state management

---

## 👨‍💻 Author

**MD Swaley**

Java Backend Developer | Spring Boot | Microservices

---

## ⭐ Project

If you find this project useful, consider giving the repository a ⭐.

**Repository:**
https://github.com/mdswaley/Telecom-Service-Provisioning-Platform
