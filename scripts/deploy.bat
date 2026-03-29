@echo off
setlocal enabledelayedexpansion

cd /d "%~dp0\.."

set ENV=%1
if "%ENV%"=="" set ENV=dev

set VERSION=%2
if "%VERSION%"=="" set VERSION=latest

set ENV_FILE=config\.env.!ENV!
set COMPOSE_FILES=-f docker-compose.yml -f docker-compose.!ENV!.yml

echo 🚀 Deploying campus_entrustment [!ENV!] version=!VERSION!

set APP_NAME=campus_entrustment
set IMAGE_TAG=!VERSION!

docker compose !COMPOSE_FILES! --env-file !ENV_FILE! pull
docker compose !COMPOSE_FILES! --env-file !ENV_FILE! up -d --remove-orphans

echo ✅ Done. Services:
docker compose !COMPOSE_FILES! ps