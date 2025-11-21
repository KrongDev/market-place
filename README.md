# Market Place

A comprehensive marketplace platform for game item trading with real-time chat and currency exchange.

## Project Structure

```
market-place/
├── client/          # Next.js frontend (TypeScript)
├── server/          # Spring Boot microservices (Java)
│   ├── user-service/
│   ├── exchange-service/
│   ├── chat-service/
│   └── file-service/
├── infra/           # Infrastructure configuration
└── docker-compose.yml
```

## Features

- **Item Exchange**: Buy and sell game items
- **Currency Exchange**: Trade in-game currency
- **Real-time Chat**: WebSocket-based chat for trade negotiations
- **Community**: Discussion forums
- **Admin Panel**: User and trade management

## Tech Stack

### Frontend
- Next.js 14 (TypeScript)
- React 18
- Axios for API calls
- WebSocket (SockJS + STOMP)

### Backend
- Spring Boot 3.x
- PostgreSQL
- WebSocket
- Microservices architecture

## Getting Started

### Prerequisites
- Docker & Docker Compose
- Node.js 18+ (for local development)
- Java 17+ (for local development)

### Running with Docker

```bash
# Start all services
docker compose up

# Start specific service
docker compose up client
docker compose up user-service
```

### Access Points

- **Client**: http://localhost:3000
- **User Service**: http://localhost:8081
- **Exchange Service**: http://localhost:8082
- **Chat Service**: http://localhost:8084
- **File Service**: http://localhost:8085

## Development

### Client (TypeScript Migration Completed)

The client has been fully migrated to TypeScript with comprehensive type definitions.

```bash
cd client
npm install
npm run dev
```

### Server

Each microservice can be run independently:

```bash
cd server/user-service
./gradlew bootRun
```

## API Documentation

API endpoints are documented in the `docs/` directory.

## License

MIT
