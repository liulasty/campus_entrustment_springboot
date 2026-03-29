#!/usr/bin/env bash
# Auto-derives version from Git
VERSION=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.1.0")
BUILD=$(git rev-parse --short HEAD 2>/dev/null || echo "dev")
FULL_TAG="${VERSION}-${BUILD}"
echo "${FULL_TAG}"
