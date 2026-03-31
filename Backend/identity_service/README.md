# Identity Service Documentation

## 📋 Overview

Identity Service là microservice chịu trách nhiệm xác thực và quản lý người dùng trong hệ thống TicketHub. Service này cung cấp JWT-based authentication, user registration, và user management.

## 🚀 Features

- **User Registration** - Đăng ký tài khoản mới với validation
- **User Authentication** - Đăng nhập với JWT tokens
- **Token Management** - Refresh và logout tokens
- **User Management** - Quản lý thông tin người dùng
- **Role-based Access Control** - Phân quyền theo vai trò
- **Health Monitoring** - Health checks cho monitoring

## 🔧 Technology Stack

- **Spring Boot 3.3.5** - Main framework
- **Spring Security 6** - Authentication & Authorization
- **JWT (JSON Web Tokens)** - Token-based authentication
- **PostgreSQL 15** - Database
- **Docker & Docker Compose** - Containerization
- **Maven** - Build tool

## 📡 API Endpoints

### 🔐 Authentication

#### Register New User
```http
POST /auth/register
Content-Type: application/json

{
    "fullName": "Test User",
    "email": "user@test.com",
    "password": "password123",
    "phone": "0123456789",
    "avatarUrl": "",
    "captchaToken": "",
    "captchaPassed": true,
    "captchaScore": 1.0
}
```

**Response:**
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 3600000
}
```

#### User Login
```http
POST /auth/login
Content-Type: application/json

{
    "email": "user@test.com",
    "password": "password123",
    "captchaToken": "",
    "captchaPassed": true,
    "captchaScore": 1.0
}
```

#### Refresh Token
```http
POST /auth/refresh
Content-Type: application/json

{
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#### Logout
```http
POST /auth/logout
Content-Type: application/json

{
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 👥 User Management

#### Get All Users
```http
GET /users
Authorization: Bearer {accessToken}
```

#### Get User by ID
```http
GET /users/{id}
Authorization: Bearer {accessToken}
```

### 🏥 Health Check

#### Service Health
```http
GET /actuator/health
```

## 🐳 Docker Deployment

### Prerequisites
- Docker & Docker Compose installed
- Network `tickethub-network` created

### Start Service
```bash
cd identity_service/indentity
docker-compose -f compose.yml up -d --build
```

### Environment Variables
```yaml
environment:
  DB_HOST: postgres
  DB_PORT: 5433
  DB_NAME: identity-service
  DB_USER: postgres
  DB_PASSWORD: 270504
  JWT_SECRET: replace-with-at-least-32-characters-secret
```

## 🔒 Security Features

### JWT Token Structure
```json
{
  "sub": "user@test.com",
  "uid": "user-uuid",
  "role": "ROLE_CUSTOMER",
  "type": "access",
  "iat": 1720000000,
  "exp": 1720003600
}
```

### User Roles
- **ROLE_CUSTOMER** - Người dùng thông thường
- **ROLE_ORGANIZER** - Người tổ chức sự kiện
- **ROLE_ADMIN** - Quản trị viên hệ thống

### Security Configuration
- Password encryption with BCrypt
- JWT token expiration: 1 hour (access), 7 days (refresh)
- CORS configuration for cross-origin requests
- Request validation and sanitization

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users_ref (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    avatar_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🧪 Testing

### Test Registration
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d @test-register.json
```

### Test Login
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d @test-login.json
```

## 🔧 Configuration

### Application Properties
```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:postgresql://postgres:5433/identity-service
spring.datasource.username=postgres
spring.datasource.password=270504

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
app.jwt.secret=replace-with-at-least-32-characters-secret
app.jwt.expiration=3600000
app.jwt.refresh-expiration=604800000
```

## 🚨 Troubleshooting

### Common Issues

1. **400 Bad Request on Registration**
   - Ensure all required fields are included
   - Check email format validity
   - Verify password length (min 8 characters)

2. **403 Forbidden**
   - Check JWT token validity
   - Verify user permissions for endpoint

3. **Database Connection Issues**
   - Ensure PostgreSQL container is running
   - Check network connectivity
   - Verify environment variables

### Health Check Issues
```bash
# Check container status
docker ps | grep identity

# View logs
docker logs identity-service-dev

# Test database connection
docker exec identity-postgres pg_isready -U postgres
```

## 📊 Monitoring

### Actuator Endpoints
- `/actuator/health` - Service health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Performance metrics

### Log Levels
```properties
logging.level.com.tickethub.indentity=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🔗 Integration

### API Gateway Integration
```
Gateway (8080) → Identity Service (8081)
/api/auth/* → /auth/*
```

### Service Communication
- **Database**: PostgreSQL on port 5433
- **Network**: tickethub-network
- **Health Check**: Every 30 seconds

## 📝 Development Notes

### Running Locally
```bash
mvn spring-boot:run
```

### Building Docker Image
```bash
docker build -t identity-service .
```

### Environment Profiles
- `dev` - Development environment
- `prod` - Production environment

---

**Last Updated:** March 31, 2026
**Version:** 1.0.0
**Maintainer:** TicketHub Team
