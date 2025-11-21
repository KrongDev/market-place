# Market Place

A comprehensive marketplace platform for game item trading with real-time chat and currency exchange, built with modern microservices architecture.

[![Build and Test](https://github.com/KrongDev/market-place/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/KrongDev/market-place/actions/workflows/build-and-test.yml)
[![Publish Common Library](https://github.com/KrongDev/market-place/actions/workflows/publish-common.yml/badge.svg)](https://github.com/KrongDev/market-place/actions/workflows/publish-common.yml)

## 🚀 Features

- **Item Exchange** - Buy and sell game items with secure transactions
- **Currency Exchange** - Trade in-game currency with real-time rates
- **Real-time Chat** - WebSocket-based chat for trade negotiations
- **Community Forums** - Discussion boards for users
- **Admin Dashboard** - Comprehensive management interface
- **TypeScript Client** - Type-safe Next.js frontend

## 🏗️ Architecture

```
┌─────────────┐
│   Client    │ Next.js 14 (TypeScript)
│  Port 3000  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Gateway   │ Nginx
│   Port 80   │
└──────┬──────┘
       │
       ├──────────┬──────────┬──────────┬──────────┬──────────┐
       ▼          ▼          ▼          ▼          ▼          ▼
   ┌──────┐  ┌──────┐  ┌──────┐  ┌──────┐  ┌──────┐  ┌──────┐
   │ User │  │Exch. │  │Curr. │  │ Chat │  │Comm. │  │ File │
   │:8081 │  │:8082 │  │:8083 │  │:8084 │  │:8085 │  │:8086 │
   └──┬───┘  └──┬───┘  └──┬───┘  └──┬───┘  └──┬───┘  └──────┘
      │         │         │         │         │
      ▼         ▼         ▼         ▼         ▼
   ┌─────────────┐    ┌─────────────┐
   │    MySQL    │    │   MongoDB   │
   │    :3306    │    │   :27017    │
   └─────────────┘    └─────────────┘
```

## 🛠️ Tech Stack

### Frontend
- **Framework**: Next.js 14 with TypeScript
- **Styling**: CSS Modules
- **State**: React Hooks
- **API**: Axios
- **WebSocket**: SockJS + STOMP

### Backend
- **Framework**: Spring Boot 3.2
- **Language**: Java 21
- **Databases**: MySQL 8.0, MongoDB 6.0
- **Messaging**: Apache Kafka
- **Architecture**: Microservices

### DevOps
- **Containerization**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Package Registry**: GitHub Packages
- **Reverse Proxy**: Nginx

## 🚦 Quick Start

### Prerequisites
- Docker & Docker Compose
- Node.js 18+ (for local development)
- Java 21+ (for local development)

### Running with Docker

```bash
# Clone the repository
git clone https://github.com/KrongDev/market-place.git
cd market-place

# Start all services
docker compose up -d

# View logs
docker compose logs -f

# Stop services
docker compose down
```

### Access Points

- **Client**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **User Service**: http://localhost:8081
- **Exchange Service**: http://localhost:8082
- **Currency Service**: http://localhost:8083
- **Chat Service**: http://localhost:8084
- **Community Service**: http://localhost:8085
- **File Service**: http://localhost:8086

## 📚 Documentation

- [Deployment Guide](DEPLOYMENT.md) - Production deployment instructions
- [Contributing Guide](CONTRIBUTING.md) - How to contribute
- [GitHub Packages Setup](docs/GITHUB_PACKAGES.md) - Using the common library
- [API Documentation](docs/) - Service API references

## 🔧 Development

### Client Development

```bash
cd client
npm install
npm run dev
```

### Service Development

```bash
cd server/SERVICE_NAME
gradle bootRun
```

### Running Tests

```bash
# Client
cd client && npm test

# Services
cd server/SERVICE_NAME && gradle test
```

## 📦 Project Structure

```
market-place/
├── client/              # Next.js TypeScript frontend
│   ├── src/
│   │   ├── app/        # Pages (App Router)
│   │   ├── components/ # React components
│   │   └── services/   # API services
│   └── Dockerfile
├── server/             # Spring Boot microservices
│   ├── user-service/
│   ├── exchange-service/
│   ├── chat-service/
│   ├── community-service/
│   ├── currency-service/
│   ├── file-service/
│   └── market-place-common/  # Shared library
├── docs/               # Documentation
├── .github/
│   └── workflows/      # CI/CD pipelines
└── docker-compose.yml
```

## 🔐 Security

- JWT-based authentication
- Password encryption with BCrypt
- CORS configuration
- Rate limiting (planned)
- SQL injection prevention
- XSS protection

## 📈 Monitoring

Health check endpoints available at:
- `/actuator/health` - Service health
- `/actuator/info` - Service information
- `/actuator/metrics` - Performance metrics

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- **KrongDev** - [GitHub](https://github.com/KrongDev)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Next.js team for the amazing React framework
- All contributors who help improve this project
