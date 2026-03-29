param([string]$Env = "dev")
$ErrorActionPreference = "Stop"
Set-Location -Path $PSScriptRoot\..

$EnvFile = "config\.env.$Env"
$ComposeFiles = "-f docker-compose.yml -f docker-compose.$Env.yml"

Write-Host "🛑 Stopping campus_entrustment [$Env]..." -ForegroundColor Cyan
Invoke-Expression "docker compose $ComposeFiles --env-file $EnvFile down"
Write-Host "✅ Stopped." -ForegroundColor Green
