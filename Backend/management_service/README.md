# Management Service Documentation

## 📋 Overview

Management Service là microservice trung tâm quản lý các sự kiện, người dùng tổ chức, và các hoạt động liên quan trong hệ thống TicketHub. Service này xử lý việc tạo, quản lý events, users với vai trò organizer, và các tác vụ quản lý.

## 🚀 Features

- **Event Management** - Tạo, cập nhật, xóa sự kiện
- **User Management** - Quản lý người dùng (đặc biệt organizer)
- **Event Analytics** - Thống kê và phân tích sự kiện
- **Seat Management** - Quản lý sơ đồ ghế ngồi
- **Ticket Management** - Quản lý vé sự kiện
- **Approval Workflow** - Quy trình phê duyệt sự kiện
- **Health Monitoring** - Health checks và monitoring

## 🔧 Technology Stack

- **Spring Boot 3.3.5** - Main framework
- **Spring Data JPA** - Database operations
- **PostgreSQL 15** - Primary database
- **Redis 7** - Caching và session management
- **Spring Cloud Stream** - Message streaming (Kafka)
- **Docker & Docker Compose** - Containerization
- **Maven** - Build tool

## 📡 API Endpoints

### 🎪 Event Management

#### Create New Event
```http
POST /events?organizerId={organizerId}
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "title": "Summer Music Festival 2024",
    "description": "Annual music festival featuring top artists",
    "venue": "Central Park Arena",
    "city": "Ho Chi Minh City",
    "locationCoords": "10.7769,106.7009",
    "startTime": "2024-12-31T20:00:00",
    "endTime": "2024-12-31T23:00:00",
    "bannerUrl": "https://example.com/banner.jpg"
}
```

**Response:**
```json
{
    "id": 1,
    "title": "Summer Music Festival 2024",
    "description": "Annual music festival featuring top artists",
    "venue": "Central Park Arena",
    "city": "Ho Chi Minh City",
    "locationCoords": "10.7769,106.7009",
    "startTime": "2024-12-31T20:00:00",
    "endTime": "2024-12-31T23:00:00",
    "bannerUrl": "https://example.com/banner.jpg",
    "status": "DRAFT",
    "isPublished": false,
    "createdAt": "2024-03-31T08:00:00",
    "updatedAt": "2024-03-31T08:00:00",
    "organizer": {
        "id": 2,
        "fullName": "Event Organizer",
        "email": "organizer@test.com",
        "role": "ORGANIZER"
    }
}
```

#### Get All Events
```http
GET /events
Authorization: Bearer {accessToken}
```

#### Get Event by ID
```http
GET /events/{id}
Authorization: Bearer {accessToken}
```

#### Update Event
```http
PUT /events/{id}
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "title": "Updated Event Title",
    "description": "Updated description",
    "status": "PUBLISHED",
    "isPublished": true
}
```

#### Delete Event
```http
DELETE /events/{id}
Authorization: Bearer {accessToken}
```

#### Get Events by Organizer
```http
GET /events/organizer/{organizerId}
Authorization: Bearer {accessToken}
```

### 👥 User Management

#### Create User (Direct)
```http
POST /users
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "fullName": "Event Organizer",
    "email": "organizer@test.com",
    "role": "ORGANIZER"
}
```

**Response:**
```json
{
    "id": 2,
    "fullName": "Event Organizer",
    "email": "organizer@test.com",
    "role": "ORGANIZER",
    "syncedAt": "2024-03-31T08:00:00"
}
```

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

#### Update User
```http
PUT /users/{id}
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "fullName": "Updated Name",
    "role": "ADMIN"
}
```

#### Delete User
```http
DELETE /users/{id}
Authorization: Bearer {accessToken}
```

### 🎫 Ticket Management

#### Get Event Tickets
```http
GET /tickets/event/{eventId}
Authorization: Bearer {accessToken}
```

#### Create Ticket Type
```http
POST /tickets
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "eventId": 1,
    "type": "VIP",
    "price": 1500000,
    "quantity": 100,
    "description": "VIP ticket with backstage access"
}
```

### 💺 Seat Management

#### Get Seat Map
```http
GET /seats/event/{eventId}
Authorization: Bearer {accessToken}
```

#### Create Seat Map
```http
POST /seats
Content-Type: application/json
Authorization: Bearer {accessToken}

{
    "eventId": 1,
    "totalSeats": 1000,
    "rows": 20,
    "seatsPerRow": 50,
    "seatConfiguration": "..."
}
```

### 📊 Analytics

#### Get Event Analytics
```http
GET /analytics/event/{eventId}
Authorization: Bearer {accessToken}
```

**Response:**
```json
{
    "eventId": 1,
    "totalTicketsSold": 750,
    "totalRevenue": 1125000000,
    "attendanceRate": 75.5,
    "popularTicketTypes": [
        {"type": "VIP", "sold": 150, "revenue": 225000000},
        {"type": "Regular", "sold": 600, "revenue": 900000000}
    ]
}
```

### ✅ Approval Workflow

#### Get Pending Approvals
```http
GET /approvals/pending
Authorization: Bearer {accessToken}
```

#### Approve Event
```http
POST /approvals/{eventId}/approve
Authorization: Bearer {accessToken}

{
    "approved": true,
    "comments": "Event approved for publication"
}
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
cd management_service/management
docker-compose -f compose.yaml up -d --build
```

### Environment Variables
```yaml
environment:
  SPRING_PROFILES_ACTIVE: dev
  DB_HOST: postgres-dev
  DB_PORT: 5432
  DB_NAME: management-service
  DB_USER: postgres
  DB_PASSWORD: 270504
  REDIS_HOST: redis-dev
  REDIS_PORT: 6379
```

## 🗄️ Database Schema

### Events Table
```sql
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    venue VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    location_coords VARCHAR(50),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    banner_url TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    organizer_id UUID NOT NULL,
    FOREIGN KEY (organizer_id) REFERENCES users_ref(id)
);
```

### Users Table
```sql
CREATE TABLE users_ref (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    synced_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tickets Table
```sql
CREATE TABLE tickets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity_available INTEGER NOT NULL,
    quantity_sold INTEGER DEFAULT 0,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id)
);
```

## 🎯 Event Status Flow

```
DRAFT → PUBLISHED → ONGOING → COMPLETED
   ↓         ↓         ↓         ↓
CANCELLED CANCELLED CANCELLED CANCELLED
```

### Status Descriptions
- **DRAFT** - Event đang được tạo, chưa công bố
- **PUBLISHED** - Event đã công bố, có thể bán vé
- **ONGOING** - Event đang diễn ra
- **COMPLETED** - Event đã kết thúc
- **CANCELLED** - Event bị hủy

## 🧪 Testing

### Test Event Creation
```bash
curl -X POST http://localhost:8082/events?organizerId=2 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d @event-sample.json
```

### Test User Creation
```bash
curl -X POST http://localhost:8082/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d @user-sample.json
```

## 🔧 Configuration

### Application Properties
```properties
# Server
server.port=8082

# Database
spring.datasource.url=jdbc:postgresql://postgres-dev:5432/management-service
spring.datasource.username=postgres
spring.datasource.password=270504

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Redis
spring.redis.host=redis-dev
spring.redis.port=6379
spring.redis.timeout=2000ms

# Kafka
spring.kafka.bootstrap-servers=kafka-dev:9092
```

## 🚨 Troubleshooting

### Common Issues

1. **404 Not Found on Event Creation**
   - Verify organizer ID exists
   - Check request body format
   - Ensure user has ORGANIZER role

2. **Database Connection Issues**
   - Ensure PostgreSQL container is healthy
   - Check network connectivity
   - Verify environment variables

3. **Redis Connection Issues**
   - Ensure Redis container is running
   - Check Redis configuration
   - Verify network settings

### Health Check Issues
```bash
# Check container status
docker ps | grep management

# View logs
docker logs management-service-dev

# Test database connection
docker exec management-postgres pg_isready -U postgres

# Test Redis connection
docker exec management-redis redis-cli ping
```

## 📊 Monitoring

### Actuator Endpoints
- `/actuator/health` - Service health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Performance metrics
- `/actuator/prometheus` - Prometheus metrics

### Key Metrics
- Event creation rate
- User registration rate
- Database connection pool status
- Redis cache hit ratio
- Kafka message throughput

## 🔗 Integration

### API Gateway Integration
```
Gateway (8080) → Management Service (8082)
/api/events/* → /events/*
/api/users/* → /users/*
/api/analytics/* → /analytics/*
/api/approvals/* → /approvals/*
/api/seats/* → /seats/*
/api/tickets/* → /tickets/*
```

### External Services
- **Identity Service**: User authentication and role verification
- **Booking Service**: Ticket booking and payment processing
- **Database**: PostgreSQL for persistent data
- **Cache**: Redis for session and caching
- **Messaging**: Kafka for event streaming

## 📝 Development Notes

### Running Locally
```bash
mvn spring-boot:run
```

### Building Docker Image
```bash
docker build -t management-service .
```

### Environment Profiles
- `dev` - Development environment
- `prod` - Production environment

### Data Seeding
```bash
# Create sample organizer
curl -X POST http://localhost:8082/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Sample Organizer","email":"organizer@test.com","role":"ORGANIZER"}'
```

## 🔄 Kafka Events

### Published Events
- `event.created` - New event created
- `event.updated` - Event updated
- `event.published` - Event published
- `user.created` - New user created

### Consumed Events
- `user.synced` - User synced from Identity Service
- `payment.completed` - Payment completed for tickets

---

**Last Updated:** March 31, 2026
**Version:** 1.0.0
**Maintainer:** TicketHub Team
