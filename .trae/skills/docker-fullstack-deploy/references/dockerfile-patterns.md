# Dockerfile Patterns Reference

## Spring Boot Multi-Stage

```dockerfile
# ---- Build Stage ----
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /build

# Cache dependencies layer separately
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /build/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser

# Tune JVM for container memory
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

## Vue + Nginx Multi-Stage

```dockerfile
# ---- Build Stage ----
FROM node:20-alpine AS builder
WORKDIR /app

# Cache node_modules layer
COPY package*.json ./
RUN npm ci --prefer-offline

COPY . .

# Inject API URL at build time
ARG VITE_API_BASE_URL=http://localhost:8080
ENV VITE_API_BASE_URL=${VITE_API_BASE_URL}

RUN npm run build

# ---- Runtime Stage ----
FROM nginx:stable-alpine AS runtime

# Remove default config
RUN rm /etc/nginx/conf.d/default.conf
COPY docker/frontend/nginx.conf /etc/nginx/conf.d/app.conf

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD wget -qO- http://localhost:80/ || exit 1
```

## Nginx Reverse Proxy Dockerfile

```dockerfile
FROM nginx:stable-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY docker/nginx/nginx.conf /etc/nginx/conf.d/app.conf
EXPOSE 80 443
```

## Frontend Nginx Site Config (nginx.conf)

```nginx
server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;

    # SPA fallback
    location / {
        try_files $uri $uri/ /index.html;
    }

    # Proxy API calls to backend
    location /api/ {
        proxy_pass http://backend:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_read_timeout 60s;
    }

    # Static asset caching
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff2?)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
```

## Reverse Proxy Gateway Nginx Config

```nginx
upstream backend {
    server backend:8080;
}

upstream frontend {
    server frontend:80;
}

server {
    listen 80;
    server_name _;

    # Frontend
    location / {
        proxy_pass http://frontend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # Backend API
    location /api/ {
        proxy_pass http://backend/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_read_timeout 120s;
    }
}
```

## .dockerignore Template

```
# Version control
.git
.gitignore

# Build artifacts
target/
node_modules/
dist/
build/

# IDE files
.idea/
.vscode/
*.iml

# Environment files (never bake secrets into image)
.env*
config/

# Logs
*.log
logs/

# Test files
src/test/
**/*.test.*
**/*.spec.*

# Docker files themselves
docker-compose*.yml
docker/
scripts/

# OS artifacts
.DS_Store
Thumbs.db
```
