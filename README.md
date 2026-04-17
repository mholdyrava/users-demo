# users-demo
Simple Spring Boot rest api app with H2 in memory to demo claude code skills

## Summary

This is a Spring Boot REST API for managing users and roles, backed by an H2 in-memory database. It exposes endpoints to create users and roles, list them, and assign roles to users. The API is documented with Swagger UI and includes Spring Actuator endpoints for health and runtime monitoring.

## Run locally

Build and start the application with Maven:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Once running, explore the API via Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

To stop the application, press `Ctrl+C` in the terminal where it is running.
