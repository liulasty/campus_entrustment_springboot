# Extensions Reference

Read the relevant section when the user requests a specific extension.

---

## GitHub Actions CI/CD

```yaml
# .github/workflows/deploy.yml
name: Build & Deploy

on:
  push:
    tags: ['v*.*.*']

env:
  REGISTRY: ghcr.io
  APP_NAME: myapp

jobs:
  build-and-push:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write

    steps:
      - uses: actions/checkout@v4

      - name: Set up QEMU (multi-arch)
        uses: docker/setup-qemu-action@v3

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3

      - name: Log in to GHCR
        uses: docker/login-action@v3
        with:
          registry: ${{ env.REGISTRY }}
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      - name: Extract version from tag
        id: version
        run: echo "VERSION=${GITHUB_REF_NAME#v}" >> $GITHUB_OUTPUT

      - name: Build and push backend
        uses: docker/build-push-action@v5
        with:
          context: .
          file: docker/backend/Dockerfile
          platforms: linux/amd64,linux/arm64
          push: true
          tags: |
            ${{ env.REGISTRY }}/${{ github.repository_owner }}/${{ env.APP_NAME }}-backend:${{ steps.version.outputs.VERSION }}
            ${{ env.REGISTRY }}/${{ github.repository_owner }}/${{ env.APP_NAME }}-backend:latest
          cache-from: type=gha
          cache-to: type=gha,mode=max

      - name: Build and push frontend
        uses: docker/build-push-action@v5
        with:
          context: .
          file: docker/frontend/Dockerfile
          platforms: linux/amd64,linux/arm64
          push: true
          build-args: |
            VITE_API_BASE_URL=${{ secrets.VITE_API_BASE_URL }}
          tags: |
            ${{ env.REGISTRY }}/${{ github.repository_owner }}/${{ env.APP_NAME }}-frontend:${{ steps.version.outputs.VERSION }}
            ${{ env.REGISTRY }}/${{ github.repository_owner }}/${{ env.APP_NAME }}-frontend:latest

  deploy:
    needs: build-and-push
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to server via SSH
        uses: appleboy/ssh-action@v1
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SSH_PRIVATE_KEY }}
          script: |
            cd /opt/myapp
            IMAGE_TAG=${{ steps.version.outputs.VERSION }} ./scripts/deploy.sh prod
```

---

## Watchtower (Auto-update containers)

Add to `docker-compose.prod.yml`:

```yaml
  watchtower:
    image: containrrr/watchtower
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      WATCHTOWER_POLL_INTERVAL: 300       # Check every 5 minutes
      WATCHTOWER_CLEANUP: "true"          # Remove old images
      WATCHTOWER_INCLUDE_STOPPED: "false"
      WATCHTOWER_NOTIFICATIONS: slack
      WATCHTOWER_NOTIFICATION_SLACK_HOOK_URL: ${SLACK_WEBHOOK_URL}
    restart: unless-stopped
```

---

## SSL/TLS with Let's Encrypt (Nginx + Certbot)

```yaml
# Add to docker-compose.prod.yml
  nginx:
    volumes:
      - certbot_conf:/etc/letsencrypt
      - certbot_www:/var/www/certbot
    ports:
      - "80:80"
      - "443:443"

  certbot:
    image: certbot/certbot
    volumes:
      - certbot_conf:/etc/letsencrypt
      - certbot_www:/var/www/certbot
    entrypoint: "/bin/sh -c 'trap exit TERM; while :; do certbot renew; sleep 12h & wait $${!}; done'"

volumes:
  certbot_conf:
  certbot_www:
```

Nginx HTTPS config:
```nginx
server {
    listen 80;
    location /.well-known/acme-challenge/ { root /var/www/certbot; }
    location / { return 301 https://$host$request_uri; }
}
server {
    listen 443 ssl;
    ssl_certificate     /etc/letsencrypt/live/yourdomain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/yourdomain.com/privkey.pem;
    # ... rest of config
}
```

Obtain cert (first time):
```bash
docker compose run --rm certbot certonly --webroot \
  --webroot-path /var/www/certbot \
  --email admin@yourdomain.com \
  --agree-tos \
  -d yourdomain.com
```

---

## Redis Cache Service

```yaml
  redis:
    image: redis:7-alpine
    command: redis-server --requirepass ${REDIS_PASSWORD}
    volumes:
      - redis_data:/data
    networks:
      - appnet
    healthcheck:
      test: ["CMD", "redis-cli", "-a", "${REDIS_PASSWORD}", "ping"]
      interval: 10s
      timeout: 5s
      retries: 3
    restart: unless-stopped

volumes:
  redis_data:
    name: ${APP_NAME}-redis-data
```

Add to `.env.*`:
```dotenv
REDIS_HOST=redis
REDIS_PORT=6379
REDIS_PASSWORD=redis_secret
```

Spring Boot `application.yml`:
```yaml
spring:
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:}
```

---

## Kubernetes Manifest (basic)

Generate from existing compose using Kompose:
```bash
kompose convert -f docker-compose.yml -f docker-compose.prod.yml -o k8s/
```

Or produce hand-crafted Deployment + Service + ConfigMap + Secret skeleton:

```yaml
# k8s/backend-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend
spec:
  replicas: 2
  selector:
    matchLabels:
      app: backend
  template:
    metadata:
      labels:
        app: backend
    spec:
      containers:
        - name: backend
          image: myorg/myapp-backend:1.0.0
          ports:
            - containerPort: 8080
          envFrom:
            - configMapRef:
                name: app-config
            - secretRef:
                name: app-secrets
          readinessProbe:
            httpGet:
              path: /actuator/health
              port: 8080
            initialDelaySeconds: 30
```
