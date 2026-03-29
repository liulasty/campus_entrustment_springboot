# Environment Configuration Reference

## .env File Design Principles

1. **One file per environment** — `.env.dev` and `.env.prod` each stand alone
2. **Every variable used in compose is defined** in both files (no missing keys)
3. **Sensitive files in .gitignore** — commit only `.env.example`
4. **No logic in .env** — just key=value pairs; logic belongs in scripts

## .env.dev Template

```dotenv
# ============================================================
# DEVELOPMENT ENVIRONMENT CONFIG
# Usage: docker compose ... --env-file config/.env.dev up
# ============================================================

# === Application ===
APP_NAME=myapp
IMAGE_TAG=dev
REGISTRY=

# === Spring Boot ===
SPRING_PROFILES_ACTIVE=dev
BACKEND_PORT=8080
JAVA_OPTS=-Xmx512m -Xms256m

# === Vue / Frontend ===
FRONTEND_PORT=5173
VITE_API_BASE_URL=http://localhost:8080

# === Database ===
DB_HOST=db
DB_PORT=3306
DB_NAME=appdb_dev
DB_USER=appuser
DB_PASSWORD=dev_password_123
DB_ROOT_PASSWORD=dev_root_123
```

## .env.prod Template

```dotenv
# ============================================================
# PRODUCTION ENVIRONMENT CONFIG
# SENSITIVE — DO NOT COMMIT — listed in .gitignore
# Usage: docker compose ... --env-file config/.env.prod up -d
# ============================================================

# === Application ===
APP_NAME=myapp
IMAGE_TAG=1.0.0
REGISTRY=docker.io/myorg/

# === Spring Boot ===
SPRING_PROFILES_ACTIVE=prod
BACKEND_PORT=8080
JAVA_OPTS=-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom

# === Vue / Frontend ===
FRONTEND_PORT=80
VITE_API_BASE_URL=https://api.yourdomain.com

# === Database ===
DB_HOST=db
DB_PORT=3306
DB_NAME=appdb_prod
DB_USER=appuser
DB_PASSWORD=REPLACE_WITH_STRONG_PASSWORD
DB_ROOT_PASSWORD=REPLACE_WITH_STRONG_ROOT_PASSWORD
```

## .env.example (committed to Git)

```dotenv
# Copy this to .env.dev or .env.prod and fill in values
# DO NOT store real passwords here

APP_NAME=myapp
IMAGE_TAG=latest
REGISTRY=

SPRING_PROFILES_ACTIVE=dev
BACKEND_PORT=8080
JAVA_OPTS=-Xmx512m

FRONTEND_PORT=80
VITE_API_BASE_URL=http://localhost:8080

DB_HOST=db
DB_PORT=3306
DB_NAME=appdb
DB_USER=appuser
DB_PASSWORD=CHANGE_ME
DB_ROOT_PASSWORD=CHANGE_ME
```

## .gitignore additions

```gitignore
# Environment files with real credentials
config/.env.dev
config/.env.prod

# Keep the example
!config/.env.example
```

## Spring Boot application.yml (base)

```yaml
# src/main/resources/application.yml
spring:
  application:
    name: ${APP_NAME:myapp}
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}
  datasource:
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:appdb}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: ${DB_USER:appuser}
    password: ${DB_PASSWORD:changeme}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

server:
  port: ${BACKEND_PORT:8080}

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: when-authorized
```

## application-dev.yml

```yaml
# src/main/resources/application-dev.yml
spring:
  jpa:
    hibernate:
      ddl-auto: update    # Auto-create/update schema in dev
    show-sql: true
  devtools:
    restart:
      enabled: true

logging:
  level:
    root: INFO
    com.yourcompany: DEBUG
```

## application-prod.yml

```yaml
# src/main/resources/application-prod.yml
spring:
  jpa:
    hibernate:
      ddl-auto: validate   # Never modify schema in prod
    show-sql: false

server:
  compression:
    enabled: true

logging:
  level:
    root: WARN
    com.yourcompany: INFO
  file:
    name: /app/logs/application.log
```

## vite.config.ts snippet

```typescript
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  // Load env vars — VITE_ prefix exposed to client
  const env = loadEnv(mode, process.cwd(), '')

  return {
    plugins: [vue()],
    server: {
      port: 5173,
      proxy: {
        // In dev, proxy /api to Spring Boot to avoid CORS
        '/api': {
          target: env.VITE_API_BASE_URL || 'http://localhost:8080',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    },
    build: {
      outDir: 'dist',
      sourcemap: mode !== 'production'
    }
  }
})
```
