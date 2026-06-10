# Member Article Admin Project

A Spring Boot web application for managing members and articles with role-based access control.

## Features

- **Role-based access**: ADMIN and USER roles
- **Admin management**: Admin can view/delete members (cascading article deletion)
- **Article management**: Users see only their own articles; admins see all
- **Article deletion**: Users delete their own articles; admins delete any

## Tech Stack

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Security
- Thymeleaf
- H2 Database

## How to Run

```bash
cd C:\Users\mohan\AppData\Local\Temp\opencode\project
mvn spring-boot:run
```

Or double-click `run.bat`.

## Default Admin

- Username: `admin`
- Password: `admin123`

## Endpoints

| URL | Description |
|-----|-------------|
| `/login` | Login page |
| `/register` | Register new user |
| `/articles` | View articles (filtered by role) |
| `/admin/members` | Admin: manage members |

## License

MIT
