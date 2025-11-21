# Deployment Guide

## Repository
- **GitHub**: https://github.com/KrongDev/market-place.git
- **Branch**: main

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/KrongDev/market-place.git
cd market-place
```

### 2. Environment Setup

The project uses Docker Compose for easy deployment. No additional environment configuration is required for local development.

### 3. Start All Services

```bash
docker compose up -d
```

This will start:
- **Client** (Next.js): http://localhost:3000
- **User Service**: http://localhost:8081
- **Exchange Service**: http://localhost:8082
- **Chat Service**: http://localhost:8084
- **File Service**: http://localhost:8085
- **PostgreSQL Databases** (one per service)
- **Nginx Gateway**: http://localhost:80

### 4. Install Client Dependencies

Since the client was migrated to TypeScript, dependencies need to be installed:

```bash
docker compose exec client npm install
```

Or rebuild the client container:

```bash
docker compose up --build client
```

## Service Architecture

```
┌─────────────┐
│   Client    │ (Next.js TypeScript)
│  Port 3000  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Nginx    │ (API Gateway)
│   Port 80   │
└──────┬──────┘
       │
       ├─────────────────┬─────────────────┬─────────────────┐
       ▼                 ▼                 ▼                 ▼
┌──────────┐      ┌──────────┐      ┌──────────┐      ┌──────────┐
│   User   │      │ Exchange │      │   Chat   │      │   File   │
│ Service  │      │ Service  │      │ Service  │      │ Service  │
│ :8081    │      │ :8082    │      │ :8084    │      │ :8085    │
└────┬─────┘      └────┬─────┘      └────┬─────┘      └────┬─────┘
     │                 │                 │                 │
     ▼                 ▼                 ▼                 ▼
┌─────────┐      ┌─────────┐      ┌─────────┐      ┌─────────┐
│ User DB │      │Exch. DB │      │Chat DB  │      │File DB  │
└─────────┘      └─────────┘      └─────────┘      └─────────┘
```

## Development

### Client Development

```bash
cd client
npm install
npm run dev
```

### Server Development

Each microservice can be developed independently:

```bash
cd server/user-service
./gradlew bootRun
```

## Production Deployment

### Option 1: Docker Compose (Recommended for Small Scale)

```bash
# Build all services
docker compose build

# Start in production mode
docker compose up -d

# View logs
docker compose logs -f
```

### Option 2: Kubernetes (For Production Scale)

1. Build and push Docker images to a registry
2. Apply Kubernetes manifests (to be created)
3. Configure ingress and load balancing

### Option 3: Cloud Platform Deployment

#### AWS
- Use ECS/EKS for container orchestration
- RDS for PostgreSQL databases
- S3 for file storage
- CloudFront for CDN

#### GCP
- Use GKE for Kubernetes
- Cloud SQL for PostgreSQL
- Cloud Storage for files
- Cloud CDN

#### Azure
- Use AKS for Kubernetes
- Azure Database for PostgreSQL
- Blob Storage for files
- Azure CDN

## Environment Variables

### Client (.env.local)

```env
NEXT_PUBLIC_API_URL=http://localhost:80/api/v1
```

### Backend Services

Each service uses `application.yml` for configuration. Update database connections and service URLs as needed.

## Monitoring

### Health Checks

- User Service: http://localhost:8081/actuator/health
- Exchange Service: http://localhost:8082/actuator/health
- Chat Service: http://localhost:8084/actuator/health
- File Service: http://localhost:8085/actuator/health

### Logs

```bash
# View all logs
docker compose logs -f

# View specific service logs
docker compose logs -f client
docker compose logs -f user-service
```

## Troubleshooting

### Client Build Issues

If you encounter TypeScript errors:

```bash
# Rebuild client with dependencies
docker compose down client
docker compose up --build client
```

### Database Connection Issues

```bash
# Restart databases
docker compose restart user-db exchange-db chat-db file-db
```

### Port Conflicts

If ports are already in use, modify `docker-compose.yml` to use different ports.

## Backup and Restore

### Database Backup

```bash
# Backup user database
docker compose exec user-db pg_dump -U postgres marketplace_user > backup_user.sql

# Backup exchange database
docker compose exec exchange-db pg_dump -U postgres marketplace_exchange > backup_exchange.sql
```

### Database Restore

```bash
# Restore user database
docker compose exec -T user-db psql -U postgres marketplace_user < backup_user.sql
```

## Security Considerations

1. **Change default passwords** in `docker-compose.yml` for production
2. **Enable HTTPS** using SSL certificates
3. **Configure CORS** properly in backend services
4. **Use environment variables** for sensitive data
5. **Enable authentication** on all endpoints
6. **Regular security updates** for dependencies

## Performance Optimization

1. **Enable caching** in Nginx
2. **Use CDN** for static assets
3. **Database indexing** for frequently queried fields
4. **Connection pooling** for database connections
5. **Horizontal scaling** for high-traffic services

## Support

For issues or questions:
- Create an issue on GitHub
- Check documentation in `/docs` directory
- Review service-specific README files
