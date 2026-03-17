# Student Management (Mini Project)

Spring Boot REST API for managing `Student` records with full CRUD + partial updates, backed by an in-memory H2 database, plus a tiny HTML/JS frontend to show how a client talks to the API. Built to validate requests quickly in Postman and the browser.

## Tech

- **Java**: 17
- **Spring Boot**: 4.x
- **DB**: H2 (in-memory) + Spring Data JPA

## Run locally

```bash
./mvnw spring-boot:run
```

App runs at `http://localhost:8080`.

## Frontend

- Served directly by Spring Boot from `src/main/resources/static/index.html`
- Open in browser at: `http://localhost:8080/`
- Shows:
  - Student table (list, edit, delete)
  - Form to create/update students
  - All calls go through the REST API below.

## API endpoints

Base URL: `http://localhost:8080/api/students`

- **List students**: `GET /api/students`
- **Get one**: `GET /api/students/{id}`
- **Create**: `POST /api/students`
- **Update (full replace)**: `PUT /api/students/{id}`
- **Update (partial)**: `PATCH /api/students/{id}`
- **Delete**: `DELETE /api/students/{id}`

## Postman examples

### Create

`POST http://localhost:8080/api/students`

```json
{
  "firstName": "Jordan",
  "lastName": "Eldridge",
  "email": "jordan@example.com",
  "major": "Computer Science"
}
```

### Full update (PUT)

`PUT http://localhost:8080/api/students/1`

```json
{
  "firstName": "Test",
  "lastName": "Test",
  "email": "test@test.com",
  "major": "Computer Science"
}
```

### Partial update (PATCH)

`PATCH http://localhost:8080/api/students/1`

Send only what you want to change:

```json
{
  "major": "Software Engineering"
}
```

## H2 console (optional)

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:studentdb`
- **User**: `sa`
- **Password**: (blank)

