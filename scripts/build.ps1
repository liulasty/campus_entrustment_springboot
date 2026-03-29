param()
$ErrorActionPreference = "Stop"
Set-Location -Path $PSScriptRoot\..

Write-Host "🚀 Building backend image..." -ForegroundColor Cyan
docker build -t campus_entrustment-backend:latest -f docker/backend/Dockerfile .
Write-Host "✅ Build complete." -ForegroundColor Green
