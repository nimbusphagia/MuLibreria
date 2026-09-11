FROM node:24-alpine AS base

WORKDIR /app

RUN corepack enable

COPY package.json pnpm-lock.yaml pnpm-workspace.yaml ./
RUN pnpm install --frozen-lockfile

FROM base AS dev

COPY . .

EXPOSE 4200

CMD ["pnpm", "exec", "ng", "serve", "--host", "0.0.0.0"]

FROM base AS build

COPY . .
RUN pnpm run build

FROM node:24-alpine

WORKDIR /app

RUN addgroup -S angular && adduser -S angular -G angular

COPY --from=build --chown=angular:angular /app/dist/mu-libreria ./dist/mu-libreria

USER angular:angular

EXPOSE 4000
ENV PORT=4000

CMD ["node", "dist/mu-libreria/server/server.mjs"]
