@echo off
setlocal enabledelayedexpansion

cd /d "%~dp0\.."

set ENV=%1
if "%ENV%"=="" set ENV=dev

set ENV_FILE=config\.env.!ENV!
set COMPOSE_FILES=-f docker-compose.yml -f docker-compose.!ENV!.yml

echo 🛑 Stopping campus_entrustment [!ENV!]...
docker compose !COMPOSE_FILES! --env-file !ENV_FILE! down
echo ✅ Stopped.