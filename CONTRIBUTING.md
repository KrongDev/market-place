# Contributing to Market Place

Thank you for your interest in contributing to Market Place! This document provides guidelines and instructions for contributing.

## Development Setup

### Prerequisites
- Node.js 18+
- Java 21+
- Docker & Docker Compose
- Git

### Getting Started

1. **Fork and Clone**
   ```bash
   git clone https://github.com/YOUR_USERNAME/market-place.git
   cd market-place
   ```

2. **Set up Environment**
   ```bash
   # Client
   cp client/.env.example client/.env.local
   
   # Server
   cp server/.env.example server/.env
   ```

3. **Start Development Environment**
   ```bash
   docker compose up -d
   ```

## Code Style

### TypeScript/JavaScript
- Use TypeScript for all new client code
- Follow ESLint configuration
- Use meaningful variable names
- Add JSDoc comments for complex functions

### Java
- Follow Spring Boot conventions
- Use Lombok for boilerplate reduction
- Write comprehensive JavaDoc
- Follow SOLID principles

## Commit Messages

Follow conventional commits:
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes
- `refactor:` Code refactoring
- `test:` Test additions/changes
- `chore:` Build/tooling changes

Example:
```
feat(user-service): add password reset functionality

- Add password reset endpoint
- Implement email notification
- Add rate limiting
```

## Pull Request Process

1. **Create a Branch**
   ```bash
   git checkout -b feat/your-feature-name
   ```

2. **Make Changes**
   - Write clean, tested code
   - Update documentation
   - Add tests if applicable

3. **Test Locally**
   ```bash
   # Client
   cd client && npm run build && npm run lint
   
   # Services
   cd server/SERVICE_NAME && gradle build
   ```

4. **Submit PR**
   - Fill out the PR template
   - Link related issues
   - Request review

5. **Address Feedback**
   - Respond to review comments
   - Make requested changes
   - Re-request review

## Testing

### Client
```bash
cd client
npm run test
npm run lint
```

### Services
```bash
cd server/SERVICE_NAME
gradle test
```

## Questions?

- Open an issue for bugs
- Start a discussion for questions
- Check existing issues first

Thank you for contributing!
