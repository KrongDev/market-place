# Market Place - User Requirements

## Project Overview
A comprehensive platform for game item trading, currency exchange, and community interaction.
The system is built using a Microservices Architecture (MSA) to ensure scalability and maintainability.

## Domain Services & Features

### 1. User Service
**Tech Stack**: Java, Spring Boot, MySQL
- **Authentication**: Sign up, Login (JWT/OAuth2).
- **Authorization**: Role-based access control (Admin, User, etc.).
- **Profile Management**: User details, preferences.
- **Blacklist Management**: Ban/Unban users, restrict access.

### 2. Exchange Service (Item Trading)
**Tech Stack**: Java, Spring Boot, MongoDB
- **Trade Registration**: Users can list game items for sale.
- **Trade Flow**:
    1.  Seller registers item.
    2.  Buyer requests trade.
    3.  Seller accepts -> Chat room created.
    4.  Both parties confirm "Trade Complete" in chat.
    5.  Trade moves to "Completed Archive" (Receipt view).
    6.  Chat room deleted, history archived to file (linked to Receipt).
- **Trade History**: Log of all completed and cancelled trades.
- **Item Management**: Detailed item attributes (options, stats, rarity).
- **Search & Filter**: Advanced search for items based on options.
- **Transaction Recording**: Record actual transaction price (Cash/Game Money) for statistics.

### 3. Currency Exchange Service
**Tech Stack**: Java, Spring Boot, MySQL (or suitable RDBMS)
- **Exchange Registration**: Users can offer game currency for real money or other currencies.
- **Exchange Flow**: Same as Item Trade (Request -> Accept -> Chat -> Complete -> Archive).
- **Exchange History**: Transaction logs.
- **Rate Management**: Real-time or fixed exchange rates.

### 4. Community Service
**Tech Stack**: Java, Spring Boot, MySQL
- **Posts**: CRUD operations for community posts.
- **Comments**: CRUD operations for comments, supporting nested replies (threads).
- **Categories**: Board organization (General, Tips, Guilds, etc.).

### 5. File Management Service
**Tech Stack**: Java, Spring Boot, Object Storage (S3/MinIO)
- **File Upload/Download**: Handling images and attachments.
- **Image Processing**: Resizing, optimization.
- **Storage Management**: Abstraction over physical storage.

### 6. Chat Service
**Tech Stack**: Java, Spring Boot, Kafka/WebSocket, MongoDB/Cassandra (for history)
- **Real-time Chat**: 1:1 chat between Buyer and Seller when a trade window opens.
- **Chat History**: Persistent chat logs.
- **Notifications**: New message alerts.

## Infrastructure Requirements
- **Containerization**: Docker for all services.
- **Orchestration**: Kubernetes (Namespace: `market-place`).
    - Pods managed per domain.
- **Event Bus**: Kafka for Event-Driven Architecture.
- **Caching**: Redis for session management and hot data.
- **Gateway**: Nginx (or Spring Cloud Gateway) for routing.

## Client Requirements
- **Framework**: Next.js (React).
- **Styling**: Tailwind CSS (implied for modern UI).
- **Features**:
    - Responsive design.
    - Real-time updates (WebSocket).
    - SEO optimization.

## Development & Deployment
- **Repository**: Monorepo.
- **CI/CD**: GitHub Actions (Build, Test, Deploy).
- **Environment**:
    - **Dev**: Local (Docker Compose/Minikube).
    - **Prod**: Kubernetes Cluster (Future).
- **Common Library**: `market-place-common` (GitHub Package) for shared DTOs and Utils.
