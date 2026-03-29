# Docker Compose Patterns Reference

## Base docker-compose.yml (shared skeleton)

```yaml
# docker-compose.yml — shared across all environments
# Run with: docker compose -f docker-compose.yml -f docker-compose.{env}.yml --env-file config/.env.{env} up

name: ${APP_NAME:-myapp}

services:
  backend:
    image: ${REGISTRY:-}${APP_NAME:-myapp}-backend:${IMAGE_TAG:-latest}
    build:
      context: .
      dockerfile: docker/backend/Dockerfile
    environment:
      SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE:-dev}
      JAVA_OPTS: ${JAVA_OPTS:--Xmx512m}
      DB_HOST: ${DB_HOST:-db}
      DB_PORT: ${DB_PORT:-3306}
      DB_NAME: ${DB_NAME:-appdb}
      DB_USER: ${DB_USER:-appuser}
      DB_PASSWORD: ${DB_PASSWORD}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - appnet
    restart: unless-stopped

  frontend:
    image: ${REGISTRY:-}${APP_NAME:-myapp}-frontend:${IMAGE_TAG:-latest}
    build:
      context: .
      dockerfile: docker/frontend/Dockerfile
      args:
        VITE_API_BASE_URL: ${VITE_API_BASE_URL:-http://localhost:8080}
    depends_on:
      - backend
    networks:
      - appnet
    restart: unless-stopped

  db:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: ${DB_NAME:-appdb}
      MYSQL_USER: ${DB_USER:-appuser}
      MYSQL_PASSWORD: ${DB_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
    volumes:
      - db_data:/var/lib/mysql
    networks:
      - appnet
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-p${DB_ROOT_PASSWORD}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s

networks:
  appnet:
    driver: bridge
    name: ${APP_NAME:-myapp}-network

volumes:
  db_data:
    name: ${APP_NAME:-myapp}-db-data
```

## docker-compose.dev.yml (development overrides)

```yaml
# docker-compose.dev.yml — development overrides
# Hot-reload, debug ports, verbose logging

services:
  backend:
    # Build locally, don't pull
    build:
      context: .
      dockerfile: docker/backend/Dockerfile
    ports:
      - "${BACKEND_PORT:-8080}:8080"
      - "5005:5005"          # JVM remote debug port
    environment:
      JAVA_OPTS: >-
        -Xmx512m
        -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
    volumes:
      # Mount target jar for faster iteration (optional — rebuild with mvn)
      - ./target:/build/target:ro
    restart: "no"            # Don't auto-restart in dev

  frontend:
    # Dev server instead of Nginx — override image
    image: node:20-alpine
    working_dir: /app
    command: sh -c "npm ci && npm run dev -- --host 0.0.0.0 --port 5173"
    ports:
      - "${FRONTEND_PORT:-5173}:5173"
    volumes:
      - ./frontend:/app      # Hot-reload via bind mount
      - /app/node_modules    # Anonymous volume to avoid overwriting
    environment:
      VITE_API_BASE_URL: ${VITE_API_BASE_URL:-http://localhost:8080}

  db:
    ports:
      - "${DB_PORT:-3306}:3306"    # Expose DB to host for local tools
    volumes:
      - ./scripts/init.sql:/docker-entrypoint-initdb.d/init.sql:ro
```

## docker-compose.prod.yml (production overrides)

```yaml
# docker-compose.prod.yml — production overrides
# Pull pre-built images, resource limits, no bind mounts

services:
  backend:
    # In prod, pull from registry (no build)
    image: ${REGISTRY}${APP_NAME}-backend:${IMAGE_TAG}
    build: !reset null
    ports:
      - "127.0.0.1:${BACKEND_PORT:-8080}:8080"  # Bind to localhost only (behind proxy)
    environment:
      JAVA_OPTS: "-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
    deploy:
      resources:
        limits:
          cpus: "1.0"
          memory: 1G
    logging:
      driver: "json-file"
      options:
        max-size: "50m"
        max-file: "5"

  frontend:
    image: ${REGISTRY}${APP_NAME}-frontend:${IMAGE_TAG}
    build: !reset null
    ports:
      - "${FRONTEND_PORT:-80}:80"
    deploy:
      resources:
        limits:
          cpus: "0.25"
          memory: 128M

  nginx:
    # Reverse proxy (if enabled)
    image: ${REGISTRY}${APP_NAME}-nginx:${IMAGE_TAG}
    ports:
      - "80:80"
      - "443:443"
    depends_on:
      - backend
      - frontend
    networks:
      - appnet
    restart: unless-stopped
    deploy:
      resources:
        limits:
          cpus: "0.25"
          memory: 64M

  db:
    # Never expose DB port in prod
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
    deploy:
      resources:
        limits:
          cpus: "1.0"
          memory: 1G
```

## PostgreSQL Variant (db service)

```yaml
  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - db_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER} -d ${DB_NAME}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 20s
```

## MongoDB Variant (db service)

```yaml
  db:
    image: mongo:7
    environment:
      MONGO_INITDB_ROOT_USERNAME: ${DB_USER}
      MONGO_INITDB_ROOT_PASSWORD: ${DB_PASSWORD}
      MONGO_INITDB_DATABASE: ${DB_NAME}
    volumes:
      - db_data:/data/db
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5
```
