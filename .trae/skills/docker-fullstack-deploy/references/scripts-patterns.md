# Cross-Platform Scripts Reference

All scripts come in three variants — .sh / .bat / .ps1 — with identical behavior.

---

## build scripts

### build.sh

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

source "$SCRIPT_DIR/version.sh"

ENV=${1:-dev}
PLATFORMS=${2:-linux/amd64}      # or linux/amd64,linux/arm64 for multi-arch
PUSH=${3:-false}

ENV_FILE="$PROJECT_ROOT/config/.env.${ENV}"

if [ ! -f "$ENV_FILE" ]; then
  echo "❌ Missing env file: $ENV_FILE"
  exit 1
fi

set -a; source "$ENV_FILE"; set +a

echo "🔨 Building ${APP_NAME} [${ENV}] version=${VERSION} platforms=${PLATFORMS}"

# Use buildx for multi-arch
if [[ "$PLATFORMS" == *","* ]]; then
  docker buildx build \
    --platform "$PLATFORMS" \
    --tag "${REGISTRY:-}${APP_NAME}-backend:${VERSION}" \
    --tag "${REGISTRY:-}${APP_NAME}-backend:latest" \
    ${PUSH:+--push} \
    -f "$PROJECT_ROOT/docker/backend/Dockerfile" \
    "$PROJECT_ROOT"

  docker buildx build \
    --platform "$PLATFORMS" \
    --tag "${REGISTRY:-}${APP_NAME}-frontend:${VERSION}" \
    --tag "${REGISTRY:-}${APP_NAME}-frontend:latest" \
    --build-arg "VITE_API_BASE_URL=${VITE_API_BASE_URL}" \
    ${PUSH:+--push} \
    -f "$PROJECT_ROOT/docker/frontend/Dockerfile" \
    "$PROJECT_ROOT"
else
  docker compose \
    -f "$PROJECT_ROOT/docker-compose.yml" \
    -f "$PROJECT_ROOT/docker-compose.${ENV}.yml" \
    --env-file "$ENV_FILE" \
    build --no-cache
fi

echo "✅ Build complete: ${APP_NAME} ${VERSION}"
```

### build.bat

```bat
@echo off
setlocal enabledelayedexpansion

set SCRIPT_DIR=%~dp0
set PROJECT_ROOT=%SCRIPT_DIR%..

set ENV=%1
if "%ENV%"=="" set ENV=dev

set ENV_FILE=%PROJECT_ROOT%\config\.env.%ENV%
if not exist "%ENV_FILE%" (
    echo [ERROR] Missing env file: %ENV_FILE%
    exit /b 1
)

for /f "usebackq tokens=1,* delims==" %%A in ("%ENV_FILE%") do (
    if not "%%A"=="" if not "%%A:~0,1%"=="#" set "%%A=%%B"
)

call "%SCRIPT_DIR%version.bat"

echo [BUILD] %APP_NAME% [%ENV%] version=%VERSION%

docker compose ^
    -f "%PROJECT_ROOT%\docker-compose.yml" ^
    -f "%PROJECT_ROOT%\docker-compose.%ENV%.yml" ^
    --env-file "%ENV_FILE%" ^
    build --no-cache

echo [DONE] Build complete
endlocal
```

### build.ps1

```powershell
param(
    [string]$Env = "dev",
    [string]$Platforms = "linux/amd64",
    [switch]$Push
)

$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$ProjectRoot = Split-Path -Parent $ScriptDir

$EnvFile = Join-Path $ProjectRoot "config\.env.$Env"
if (-not (Test-Path $EnvFile)) {
    Write-Error "Missing env file: $EnvFile"
    exit 1
}

# Load .env file into current environment
Get-Content $EnvFile | Where-Object { $_ -match "^\s*[^#]" -and $_ -match "=" } | ForEach-Object {
    $parts = $_ -split "=", 2
    [System.Environment]::SetEnvironmentVariable($parts[0].Trim(), $parts[1].Trim())
}

. "$ScriptDir\version.ps1"

Write-Host "[BUILD] $env:APP_NAME [$Env] version=$Version" -ForegroundColor Cyan

docker compose `
    -f "$ProjectRoot\docker-compose.yml" `
    -f "$ProjectRoot\docker-compose.$Env.yml" `
    --env-file $EnvFile `
    build --no-cache

Write-Host "[DONE] Build complete" -ForegroundColor Green
```

---

## deploy scripts

### deploy.sh

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

ENV=${1:-dev}
VERSION=${2:-latest}
ENV_FILE="$PROJECT_ROOT/config/.env.${ENV}"

if [ ! -f "$ENV_FILE" ]; then
  echo "❌ Missing env file: $ENV_FILE"
  exit 1
fi

export IMAGE_TAG="$VERSION"

echo "🚀 Deploying [$ENV] version=$VERSION"

docker compose \
  -f "$PROJECT_ROOT/docker-compose.yml" \
  -f "$PROJECT_ROOT/docker-compose.${ENV}.yml" \
  --env-file "$ENV_FILE" \
  pull 2>/dev/null || true

docker compose \
  -f "$PROJECT_ROOT/docker-compose.yml" \
  -f "$PROJECT_ROOT/docker-compose.${ENV}.yml" \
  --env-file "$ENV_FILE" \
  up -d --remove-orphans

echo "✅ Deployed. Running containers:"
docker compose \
  -f "$PROJECT_ROOT/docker-compose.yml" \
  -f "$PROJECT_ROOT/docker-compose.${ENV}.yml" \
  --env-file "$ENV_FILE" \
  ps
```

### deploy.bat

```bat
@echo off
setlocal enabledelayedexpansion

set SCRIPT_DIR=%~dp0
set PROJECT_ROOT=%SCRIPT_DIR%..

set ENV=%1
if "%ENV%"=="" set ENV=dev

set VERSION=%2
if "%VERSION%"=="" set VERSION=latest

set ENV_FILE=%PROJECT_ROOT%\config\.env.%ENV%
set IMAGE_TAG=%VERSION%

echo [DEPLOY] Environment: %ENV%  Version: %VERSION%

docker compose ^
    -f "%PROJECT_ROOT%\docker-compose.yml" ^
    -f "%PROJECT_ROOT%\docker-compose.%ENV%.yml" ^
    --env-file "%ENV_FILE%" ^
    up -d --remove-orphans

echo [DONE] Deployment complete
endlocal
```

### deploy.ps1

```powershell
param(
    [string]$Env = "dev",
    [string]$Version = "latest"
)

$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$ProjectRoot = Split-Path -Parent $ScriptDir
$EnvFile = Join-Path $ProjectRoot "config\.env.$Env"
$env:IMAGE_TAG = $Version

Write-Host "[DEPLOY] Environment: $Env  Version: $Version" -ForegroundColor Cyan

docker compose `
    -f "$ProjectRoot\docker-compose.yml" `
    -f "$ProjectRoot\docker-compose.$Env.yml" `
    --env-file $EnvFile `
    up -d --remove-orphans

Write-Host "[DONE] Deployment complete" -ForegroundColor Green

docker compose `
    -f "$ProjectRoot\docker-compose.yml" `
    -f "$ProjectRoot\docker-compose.$Env.yml" `
    --env-file $EnvFile `
    ps
```

---

## stop scripts

### stop.sh

```bash
#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
ENV=${1:-dev}
ENV_FILE="$PROJECT_ROOT/config/.env.${ENV}"

docker compose \
  -f "$PROJECT_ROOT/docker-compose.yml" \
  -f "$PROJECT_ROOT/docker-compose.${ENV}.yml" \
  --env-file "$ENV_FILE" \
  down

echo "🛑 Stopped [$ENV]"
```

### stop.bat / stop.ps1

*(Same pattern as deploy — replace `up -d` with `down`.)*

---

## version.sh

```bash
#!/usr/bin/env bash
# Source this file: source scripts/version.sh

GIT_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.1.0")
GIT_COMMIT=$(git rev-parse --short HEAD 2>/dev/null || echo "unknown")
GIT_DIRTY=$(git status --porcelain 2>/dev/null | grep -q . && echo "-dirty" || echo "")

VERSION="${GIT_TAG}"
BUILD_TAG="${GIT_TAG}-${GIT_COMMIT}${GIT_DIRTY}"

export VERSION
export BUILD_TAG

echo "📦 Version: $VERSION  Build: $BUILD_TAG"
```

## version.bat

```bat
@echo off
for /f "tokens=*" %%i in ('git describe --tags --abbrev=0 2^>nul') do set VERSION=%%i
if "%VERSION%"=="" set VERSION=0.1.0
for /f "tokens=*" %%i in ('git rev-parse --short HEAD 2^>nul') do set GIT_COMMIT=%%i
set BUILD_TAG=%VERSION%-%GIT_COMMIT%
echo [VERSION] %VERSION%  Build: %BUILD_TAG%
```

## version.ps1

```powershell
$Version = (git describe --tags --abbrev=0 2>$null) ?? "0.1.0"
$Commit  = (git rev-parse --short HEAD 2>$null) ?? "unknown"
$BuildTag = "$Version-$Commit"
Write-Host "[VERSION] $Version  Build: $BuildTag" -ForegroundColor Yellow
```
