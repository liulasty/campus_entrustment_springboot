param(
    [string]$Env = "dev",
    [string]$Version = "latest"
)
$ErrorActionPreference = "Stop"
Set-Location -Path $PSScriptRoot\..

$EnvFile = "config\.env.$Env"
$ComposeFiles = "-f docker-compose.yml -f docker-compose.$Env.yml"

Write-Host "🚀 Deploying campus_entrustment [$Env] version=$Version" -ForegroundColor Cyan

$env:APP_NAME = "campus_entrustment"
$env:IMAGE_TAG = $Version

Invoke-Expression "docker compose $ComposeFiles --env-file $EnvFile pull"
Invoke-Expression "docker compose $ComposeFiles --env-file $EnvFile up -d --remove-orphans"

Write-Host "✅ Done. Services:" -ForegroundColor Green
Invoke-Expression "docker compose $ComposeFiles ps"
