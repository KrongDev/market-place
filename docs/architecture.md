# Market Place - System Architecture

## Architecture Overview
The system follows a **Microservices Architecture (MSA)** pattern, utilizing **Event-Driven Architecture (EDA)** for inter-service communication where appropriate.

### High-Level Diagram
```mermaid
graph TD
    Client[Client (Next.js + Nginx)] --> Gateway[API Gateway / Ingress]
    
    subgraph "Kubernetes Cluster (ns: market-place)"
        Gateway --> Auth[User Service (MySQL)]
        Gateway --> Exchange[Exchange Service (MongoDB)]
        Gateway --> Currency[Currency Service (MySQL)]
        Gateway --> Community[Community Service (MySQL)]
        Gateway --> File[File Service (Object Storage)]
        Gateway --> Chat[Chat Service]
        
        Chat <--> Kafka[Kafka Cluster]
        Exchange <--> Kafka
        
        Auth -.-> Redis[Redis Cache]
    end
```

## Technology Stack

### Client Side
- **Framework**: Next.js (React)
- **Server**: Nginx (Serving static assets / Reverse Proxy)
- **Language**: TypeScript/JavaScript

### Server Side (Backend)
- **Language**: Java (Targeting JDK 21+ for stability, aiming for 25 features where applicable)
- **Framework**: Spring Boot 3.x (Targeting 4.x concepts)
- **Build Tool**: Gradle (Multi-module/Monorepo support)

### Data Layer
- **MySQL**: Structured data (User, Currency, Community).
- **MongoDB**: Unstructured/Flexible data (Exchange Items, Chat History).
- **Redis**: Caching, Session Store.

### Infrastructure & DevOps
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Message Broker**: Kafka
- **CI/CD**: GitHub Actions
- **Repository Strategy**: Monorepo

## Module Structure (Monorepo)

| Module | Type | Description |
| :--- | :--- | :--- |
| `market-place-common` | Library | Shared DTOs, Utils, Exceptions. Published to GitHub Packages. |
| `user-service` | Service | User management, Auth. |
| `exchange-service` | Service | Item trading logic. |
| `currency-service` | Service | Currency exchange logic. |
| `community-service` | Service | Forum and comments. |
| `file-service` | Service | File upload/download. |
| `chat-service` | Service | Real-time messaging. |

## Event-Driven Design
- **Kafka** will be used for:
    - Decoupling services (e.g., Trade Created -> Notify Chat Service).
    - Monitoring and Analytics data ingestion.
    - Chat message streaming (optional, depending on scale).

## Deployment Strategy
- **Local Development**: Docker Compose or Minikube.
- **Production**: Kubernetes Cluster with separate Pods for each domain service.
- **CI/CD**: Automated pipelines via GitHub Actions to build Docker images and deploy to K8s.
