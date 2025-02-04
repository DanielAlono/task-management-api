# Task Management API

A simple task management API built with Spring Boot, Spring Security, JWT Authentication, and H2 Database.

## Tecnologies:
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5

## Install:

1. Clone the repository:
   ```bash
   git clone https://github.com/tu-usuario/task-management-api.git
   cd task-management-api
   ```

2. Execute the application with Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints:
- `GET /tasks` - Lis all task.
- `POST /tasks` - Create new task.
- `GET /tasks/{id}` - Get a taks by ID.
- `PUT /tasks/{id}` - Update any task by ID.
- `DELETE /tasks/{id}` - Delete any task by ID.

## Authentication:
The API uses JWT for the authentication. You can get an access token by using the endpoint:
`POST /auth/login`.
