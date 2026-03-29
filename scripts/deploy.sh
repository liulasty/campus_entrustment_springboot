#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

ENV=${1:-dev}           # first arg: dev | prod
VERSION=${2:-latest}    # second arg: image version

ENV_FILE="config/.env.${ENV}"
COMPOSE_FILES="-f docker-compose.yml -f docker-compose.${ENV}.yml"

echo "🚀 Deploying campus_entrustment [${ENV}] version=${VERSION}"

export APP_NAME="campus_entrustment"
export IMAGE_TAG=${VERSION}
docker compose ${COMPOSE_FILES} --env-file ${ENV_FILE} pull
docker compose ${COMPOSE_FILES} --env-file ${ENV_FILE} up -d --remove-orphans

echo "✅ Done. Services:"
docker compose ${COMPOSE_FILES} ps
