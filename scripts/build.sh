#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "🚀 Building backend image..."
docker build -t campus_entrustment-backend:latest -f docker/backend/Dockerfile .
echo "✅ Build complete."
