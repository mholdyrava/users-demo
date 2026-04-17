# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Spring Boot REST API demo app using an H2 in-memory database. Stack: Java 21, Spring Boot 4.0.5, Spring Data JPA, Spring MVC, Lombok, springdoc-openapi (Swagger UI), Spring Actuator.

## Commands

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=UsersDemoApplicationTests
```

## Key URLs (when running locally)

- Swagger UI: `http://localhost:8080/swagger-ui.html` (served from `src/main/resources/static/openapi.yaml`)
- H2 Console: `http://localhost:8080/h2-console` — JDBC URL: `jdbc:h2:mem:usersdb`
- Actuator: `http://localhost:8080/actuator` (exposes `health`, `info`, `loggers`)

## Architecture

All source lives under `src/main/java/com/epam/mh/` in these packages:

| Package | Purpose |
|---------|---------|
| `entity` | JPA entities: `User`, `Role` |
| `repository` | Spring Data JPA repositories: `UserRepository`, `RoleRepository` |
| `service` | Business logic: `UserService`, `RoleService` |
| `controller` | REST controllers: `UserController`, `RoleController` |
| `model` | Request/response records (`AddUserRequest`, `AddRoleRequest`, `AddRoleToUserRequest`, `UserSummary`) |

### Domain model

- `Role` — `id`, `name`, `code` (unique, not null)
- `User` — `id`, `fullName`, `assignedRoles` (many-to-many → `user_roles` join table, `FetchType.LAZY`)

### API base path: `api/v1`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/roles` | All roles |
| POST | `/roles` | Create role (400 if code already exists) |
| GET | `/users` | All users — returns `UserSummary` (no roles loaded) |
| PATCH | `/users` | Create user |
| GET | `/users/{userId}` | User with assigned roles |
| POST | `/users/{userId}/roles` | Assign role to user (400 if already assigned) |

### Key design decisions

- `GET /users` returns `UserSummary` (id + fullName only) to avoid triggering lazy role loading.
- `GET /users/{userId}` and `POST /users/{userId}/roles` run inside `@Transactional` to force-initialize the lazy `assignedRoles` collection before the response is serialized.
- Services throw `ResponseStatusException` directly (no separate exception handler needed).