#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

ENV=${1:-dev}
ENV_FILE="config/.env.${ENV}"
COMPOSE_FILES="-f docker-compose.yml -f docker-compose.${ENV}.yml"

echo "🛑 Stopping campus_entrustment [${ENV}]..."
docker compose ${COMPOSE_FILES} --env-file ${ENV_FILE} down
echo "✅ Stopped."
