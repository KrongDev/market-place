# GitHub Packages Setup

This document explains how to use the `market-place-common` library from GitHub Packages.

## Overview

The `market-place-common` library contains shared code used across all microservices:
- Common DTOs (ApiResponse, ErrorResponse)
- Exception handling (BusinessException, ErrorCode)
- Validation utilities

## Publishing (Automated)

The library is automatically published to GitHub Packages when changes are pushed to the `server/market-place-common` directory on the `main` branch.

### Manual Publishing

To manually publish the library:

```bash
cd server/market-place-common
./gradlew publish
```

**Requirements:**
- GitHub Personal Access Token with `write:packages` permission
- Set environment variables:
  - `GITHUB_ACTOR`: Your GitHub username
  - `GITHUB_TOKEN`: Your GitHub Personal Access Token

## Using the Library

All microservices are already configured to use the library from GitHub Packages.

### Dependency Declaration

Each service's `build.gradle.kts` includes:

```kotlin
dependencies {
    implementation("com.marketplace:market-place-common:1.0.0")
}
```

### Repository Configuration

Each service's `build.gradle.kts` includes the GitHub Packages repository:

```kotlin
repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/KrongDev/market-place")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
```

## Authentication

### For Local Development

Create `~/.gradle/gradle.properties` with:

```properties
gpr.user=your-github-username
gpr.token=your-github-personal-access-token
```

**Creating a Personal Access Token:**
1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token with `read:packages` permission
3. Copy the token to `gradle.properties`

### For CI/CD (GitHub Actions)

GitHub Actions automatically provides `GITHUB_ACTOR` and `GITHUB_TOKEN` environment variables.

### For Docker

When building services in Docker, pass the credentials as build arguments:

```dockerfile
ARG GITHUB_ACTOR
ARG GITHUB_TOKEN
ENV GITHUB_ACTOR=$GITHUB_ACTOR
ENV GITHUB_TOKEN=$GITHUB_TOKEN
```

Build command:
```bash
docker build \
  --build-arg GITHUB_ACTOR=your-username \
  --build-arg GITHUB_TOKEN=your-token \
  -t service-name .
```

## Version Management

Current version: **1.0.0**

To update the version, modify `server/market-place-common/build.gradle.kts`:

```kotlin
version = "1.0.1"
```

Then publish the new version.

## Troubleshooting

### 401 Unauthorized

**Problem:** Cannot download package from GitHub Packages

**Solution:**
- Verify your GitHub token has `read:packages` permission
- Check that `GITHUB_ACTOR` and `GITHUB_TOKEN` are set correctly
- Ensure you have access to the repository

### Package Not Found

**Problem:** Package `com.marketplace:market-place-common` not found

**Solution:**
- Ensure the package has been published at least once
- Check the package exists at: https://github.com/KrongDev/market-place/packages
- Verify the version number matches

### Build Fails in Docker

**Problem:** Gradle cannot resolve dependencies in Docker build

**Solution:**
- Pass GitHub credentials as build arguments
- Ensure Docker has network access to GitHub
- Check Docker build logs for authentication errors

## Package Information

- **Group ID:** `com.marketplace`
- **Artifact ID:** `market-place-common`
- **Version:** `1.0.0`
- **Repository:** https://github.com/KrongDev/market-place
- **Package URL:** https://github.com/KrongDev/market-place/packages
