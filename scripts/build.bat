@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0\.."

echo 🚀 Building backend image...
docker build -t campus_entrustment-backend:latest -f docker/backend/Dockerfile .
echo ✅ Build complete.