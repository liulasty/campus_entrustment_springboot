---
name: docker-fullstack-deploy
description: "Design and generate complete Docker containerization and deployment systems for Spring Boot + Vue full-stack applications. Use this skill whenever the user mentions Docker, containerization, deployment scripts, docker-compose, multi-environment config, .env files, CI/CD pipelines, image versioning, multi-arch builds, or wants to \"deploy\", \"dockerize\", \"ship\", or \"run\" a Spring Boot or Vue project. Also trigger when the user asks about cross-platform startup scripts (.sh/.bat/.ps1), semantic versioning for images, or unified configuration management across dev/prod environments. Even if the user only mentions one part (e.g., \"I need a deploy script\"), apply this skill to deliver the full, cohesive system."
---

# Docker Full-Stack Deploy Skill

Generate a **production-ready, cross-platform Docker deployment system** for
Spring Boot + Vue applications. The output is a complete file tree the user
can drop into their project root and use immediately.

---

## Step 1 — Gather Context

Before generating any files, ask (or infer from context) the following.
Collect all answers in one shot — do NOT ask one question at a time.

### Required Info
| # | Question | Default if not provided |
|---|----------|------------------------|
| 1 | Project name (used as image prefix & network name) | `myapp` |
| 2 | Spring Boot port | `8080` |
| 3 | Vue / Nginx port | `80` (prod) / `5173` (dev) |
| 4 | Database? (MySQL / PostgreSQL / MongoDB / None) | MySQL |
| 5 | Database name, user, password pattern | `appdb / appuser / changeme` |
| 6 | Target environments needed (dev / prod / both) | both |
| 7 | Need reverse proxy? (Nginx as gateway / Traefik / None) | Nginx |
| 8 | Registry prefix for pushed images? (e.g. `docker.io/myorg`) | local only |
| 9 | Need multi-arch build (amd64 + arm64)? | yes |
| 10 | Git-tag-based versioning or manual? | Git tag |

If the user wants to skip questions, use all defaults and proceed.

---

## Step 2 — Generate File Tree

Produce the following structure. Read the relevant reference files before
generating each section.

```
project-root/
├── docker/
│   ├── backend/
│   │   └── Dockerfile              # Spring Boot image
│   ├── frontend/
│   │   ├── Dockerfile              # Vue prod image (Nginx)
│   │   └── nginx.conf              # Nginx site config
│   └── nginx/                      # (if reverse proxy chosen)
│       ├── Dockerfile
│       └── nginx.conf
├── scripts/
│   ├── build.sh                    # Linux/macOS build
│   ├── build.bat                   # Windows CMD build
│   ├── build.ps1                   # Windows PowerShell build
│   ├── deploy.sh
│   ├── deploy.bat
│   ├── deploy.ps1
│   ├── stop.sh / stop.bat / stop.ps1
│   └── version.sh                  # semantic version helper
├── config/
│   ├── .env.dev                    # dev environment vars
│   ├── .env.prod                   # prod environment vars
│   └── .env.example                # committed template (no secrets)
├── docker-compose.yml              # base compose (shared services)
├── docker-compose.dev.yml          # dev overrides
├── docker-compose.prod.yml         # prod overrides
└── .dockerignore
```

> **Reference files to read** before generating each section:
> - `references/dockerfile-patterns.md` — Dockerfile best practices
> - `references/compose-patterns.md`    — docker-compose patterns
> - `references/scripts-patterns.md`    — cross-platform script patterns
> - `references/versioning.md`          — semantic versioning & Git tags
> - `references/env-config.md`          — .env & Spring profile config

---

## Step 3 — Generation Rules

### 3.1 Dockerfiles

**Backend (Spring Boot)**
- Multi-stage build: `maven:3.9-eclipse-temurin-17` → `eclipse-temurin:17-jre-alpine`
- Copy only the fat JAR in the final stage
- `ENTRYPOINT ["java", "-jar", "app.jar"]` with `JAVA_OPTS` env var support
- Health check via `/actuator/health`

**Frontend (Vue)**
- Stage 1: `node:20-alpine` — runs `npm ci && npm run build`
- Stage 2: `nginx:stable-alpine` — serves `/dist`
- Inject `VITE_API_BASE_URL` at build time via `--build-arg`

### 3.2 docker-compose Structure

Use **three-file override pattern**:
```
docker-compose.yml          # shared: networks, volumes, service skeletons
docker-compose.dev.yml      # dev: bind mounts, hot-reload, debug ports
docker-compose.prod.yml     # prod: resource limits, restart policies, no bind mounts
```

Startup command:
```bash
# dev
docker compose -f docker-compose.yml -f docker-compose.dev.yml --env-file config/.env.dev up

# prod
docker compose -f docker-compose.yml -f docker-compose.prod.yml --env-file config/.env.prod up -d
```

### 3.3 Environment Variables

**config/.env.dev** and **config/.env.prod** are the single source of truth.
Each file provides ALL variables needed for one startup — no manual edits
to compose files required.

Required variable groups:

```dotenv
# === App ===
APP_NAME=myapp
APP_VERSION=1.0.0

# === Backend ===
SPRING_PROFILES_ACTIVE=dev
BACKEND_PORT=8080
JAVA_OPTS=-Xmx512m

# === Frontend ===
FRONTEND_PORT=80
VITE_API_BASE_URL=http://localhost:8080

# === Database ===
DB_HOST=db
DB_PORT=3306
DB_NAME=appdb
DB_USER=appuser
DB_PASSWORD=changeme          # ← CHANGE THIS in prod

# === Image Registry ===
REGISTRY=
IMAGE_TAG=latest
```

Spring Boot reads these via `application-{profile}.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
```

Sensitive values in `.env.prod` must appear in `.gitignore`.
`.env.example` is committed with placeholder values only.

### 3.4 Networking

Always create a named bridge network:
```yaml
networks:
  appnet:
    driver: bridge
    name: ${APP_NAME}-network
```

All services join `appnet`. Services reference each other by service name
(e.g., `DB_HOST=db`). Only expose ports to host that are explicitly needed.

### 3.5 Versioning

Image tags follow: `{registry}/{app}-{service}:{semver}-{arch}`

Example: `docker.io/myorg/myapp-backend:1.2.3-amd64`

**version.sh** — auto-derives version from Git:
```bash
VERSION=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.1.0")
BUILD=$(git rev-parse --short HEAD)
FULL_TAG="${VERSION}-${BUILD}"
```

**Multi-arch build** uses `docker buildx`:
```bash
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --tag ${REGISTRY}/${APP_NAME}-backend:${VERSION} \
  --push \
  -f docker/backend/Dockerfile .
```

### 3.6 Cross-Platform Scripts

Every script comes in three variants: `.sh` (Linux/macOS), `.bat` (CMD),
`.ps1` (PowerShell). All three are functionally identical.

**deploy.sh** structure:
```bash
#!/usr/bin/env bash
set -euo pipefail

ENV=${1:-dev}           # first arg: dev | prod
VERSION=${2:-latest}    # second arg: image version

ENV_FILE="config/.env.${ENV}"
COMPOSE_FILES="-f docker-compose.yml -f docker-compose.${ENV}.yml"

echo "🚀 Deploying ${APP_NAME} [${ENV}] version=${VERSION}"

export IMAGE_TAG=${VERSION}
docker compose ${COMPOSE_FILES} --env-file ${ENV_FILE} pull
docker compose ${COMPOSE_FILES} --env-file ${ENV_FILE} up -d --remove-orphans

echo "✅ Done. Services:"
docker compose ${COMPOSE_FILES} ps
```

**deploy.bat** uses `setlocal enabledelayedexpansion` and `%1` / `%2`.
**deploy.ps1** uses `param($Env="dev", $Version="latest")`.

---

## Step 4 — Output Format

Present the complete file tree first, then output each file in a fenced code
block labelled with its path. Use this order:

1. File tree overview
2. `docker-compose.yml`
3. `docker-compose.dev.yml`
4. `docker-compose.prod.yml`
5. `docker/backend/Dockerfile`
6. `docker/frontend/Dockerfile` + `nginx.conf`
7. `docker/nginx/` (if applicable)
8. `config/.env.dev`, `.env.prod`, `.env.example`
9. `scripts/build.*` (sh/bat/ps1)
10. `scripts/deploy.*`
11. `scripts/stop.*`
12. `scripts/version.sh`
13. `.dockerignore`
14. Spring Boot config snippet (`application.yml` + `application-dev.yml` + `application-prod.yml`)
15. Vue config snippet (`vite.config.ts` relevant parts)

After all files, provide:
- **Quick-start instructions** (copy-paste commands for dev and prod)
- **Common troubleshooting** (port conflicts, permission errors, Windows line endings)

---

## Step 5 — Quality Checklist

Before finalizing output, verify:

- [ ] All `.env.*` vars referenced in compose are defined in both env files
- [ ] No hardcoded passwords in Dockerfiles or compose files
- [ ] `.env.prod` and `.env.dev` listed in `.gitignore` snippet
- [ ] `healthcheck` defined for backend and database services
- [ ] `depends_on` uses `condition: service_healthy` not just service name
- [ ] Scripts are idempotent (safe to run multiple times)
- [ ] Windows scripts handle path separators correctly
- [ ] Multi-stage builds minimize final image size
- [ ] Named volumes used for database data (not bind mounts in prod)
- [ ] `restart: unless-stopped` on all prod services

---

## Customization Hooks

After delivering the baseline, offer these common extensions:

| Extension | Trigger phrase |
|-----------|---------------|
| Add GitHub Actions CI/CD workflow | "CI", "GitHub Actions", "pipeline" |
| Add Watchtower for auto-updates | "auto update", "watchtower" |
| Add Portainer compose | "Portainer", "GUI" |
| Add SSL/TLS with Let's Encrypt | "HTTPS", "SSL", "cert" |
| Add Redis cache service | "Redis", "cache" |
| Convert to Kubernetes manifests | "k8s", "Kubernetes", "Helm" |

Read `references/extensions.md` before implementing any extension.