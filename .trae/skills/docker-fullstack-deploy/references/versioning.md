# Versioning Reference

## Semantic Versioning Convention

Images follow: `MAJOR.MINOR.PATCH[-PRERELEASE]`

| Tag | Example | When to use |
|-----|---------|-------------|
| `{semver}` | `1.2.3` | Stable release, tied to Git tag |
| `{semver}-{short-sha}` | `1.2.3-a3f9c1` | Exact commit traceability |
| `latest` | `latest` | Always points to newest stable |
| `dev` | `dev` | Development/nightly builds |
| `{branch}-{sha}` | `feature-login-a3f9c1` | Feature branch builds |

## Git Tag Workflow

```bash
# Create a release
git tag -a v1.2.3 -m "Release 1.2.3"
git push origin v1.2.3

# The build script auto-reads this:
VERSION=$(git describe --tags --abbrev=0)   # → 1.2.3 (strips 'v' prefix)
```

Strip the `v` prefix in scripts:
```bash
VERSION=$(git describe --tags --abbrev=0 | sed 's/^v//')
```

## Full Image Tag Set (push all on release)

```bash
APP=myapp
SVC=backend
REG=docker.io/myorg
VERSION=1.2.3
COMMIT=a3f9c1

docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t ${REG}/${APP}-${SVC}:${VERSION} \
  -t ${REG}/${APP}-${SVC}:${VERSION}-${COMMIT} \
  -t ${REG}/${APP}-${SVC}:latest \
  --push \
  -f docker/backend/Dockerfile .
```

## Multi-Arch Build Setup

One-time setup (per machine):
```bash
# Install QEMU emulators
docker run --privileged --rm tonistiigi/binfmt --install all

# Create and use a multi-arch builder
docker buildx create --name multiarch --driver docker-container --use
docker buildx inspect --bootstrap
```

Build for both architectures:
```bash
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --tag myapp-backend:1.0.0 \
  --push \
  -f docker/backend/Dockerfile .
```

> Note: `--push` is required for multi-arch (cannot load multi-arch to local daemon).
> For local testing of single arch: omit `--platform` or use `--load`.

## Manifest Inspect (verify multi-arch)

```bash
docker buildx imagetools inspect myorg/myapp-backend:1.0.0
```

## IMAGE_TAG in .env files

```dotenv
# .env.dev
IMAGE_TAG=dev

# .env.prod
IMAGE_TAG=1.2.3
```

The compose files reference `${IMAGE_TAG}` — change this one variable to
roll forward or back to any version.

## Rollback Procedure

```bash
# Roll back to previous version — just change IMAGE_TAG
IMAGE_TAG=1.2.2 ./scripts/deploy.sh prod 1.2.2
```

Or edit `.env.prod`:
```dotenv
IMAGE_TAG=1.2.2
```
Then re-run `deploy.sh prod`.
