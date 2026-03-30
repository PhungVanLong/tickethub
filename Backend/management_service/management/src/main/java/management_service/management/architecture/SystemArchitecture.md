# Management Service System Architecture

## Overview
This is a comprehensive event management system built with Spring Boot and PostgreSQL, following a layered architecture pattern.

## Architecture Layers

### 1. Entity Layer (Domain Model)
**Package**: `management_service.management.entity`

**Entities**:
- **User**: User management with roles (ADMIN, ORGANIZER, STAFF, USER)
- **Event**: Event information and status management
- **EventApproval**: Event approval workflow
- **EventStaff**: Staff assignment to events (composite key)
- **SeatMap**: Venue layout management
- **Seat**: Individual seat management
- **TicketTier**: Ticket pricing and availability
- **AnalyticsEvent**: Event analytics and statistics

**Enums**:
- **UserRole**: User role definitions
- **EventStatus**: Event lifecycle states
- **ApprovalDecision**: Approval outcomes
- **RoleEventStaff**: Staff roles in events
- **SeatStatus**: Seat availability states
- **TierType**: Ticket tier categories

### 2. Repository Layer (Data Access)
**Package**: `management_service.management.repository`

**Repositories**:
- **UserRepository**: User data access with custom queries
- **EventRepository**: Event data access with search capabilities
- **EventApprovalRepository**: Approval workflow data access
- **EventStaffRepository**: Staff assignment data access
- **SeatMapRepository**: Venue layout data access
- **SeatRepository**: Seat management data access
- **TicketTierRepository**: Ticket tier data access
- **AnalyticsEventRepository**: Analytics data access

### 3. Service Layer (Business Logic)
**Package**: `management_service.management.service`

**Services**:
- **UserService**: User management operations
- **EventService**: Event lifecycle management
- **EventApprovalService**: Approval workflow logic
- **SeatMapService**: Venue layout operations
- **TicketTierService**: Ticket tier management

### 4. Controller Layer (API Endpoints)
**Package**: `management_service.management.controller`

**Controllers**:
- **UserController**: User management REST API
- **EventController**: Event management REST API
- **EventApprovalController**: Approval workflow REST API

## Database Relationships

```
USERS_REF (1) -----> (N) EVENTS (organizer)
USERS_REF (1) -----> (N) EVENT_APPROVALS (admin)
USERS_REF (1) -----> (N) EVENT_STAFF (staff)

EVENTS (1) -----> (N) ANALYTICS_EVENTS
EVENTS (1) -----> (N) EVENT_STAFF
EVENTS (1) -----> (N) SEAT_MAPS
EVENTS (1) -----> (N) EVENT_APPROVALS

SEAT_MAPS (1) -----> (N) TICKET_TIERS
SEAT_MAPS (1) -----> (N) SEATS

TICKET_TIERS (1) -----> (N) SEATS
```

## Technology Stack

- **Framework**: Spring Boot 4.0.5
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 17
- **Additional Libraries**: Lombok, Validation

## API Endpoints

### User Management
- `GET /api/users` - Get all users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/role/{role}` - Get users by role

### Event Management
- `GET /api/events` - Get all events
- `POST /api/events` - Create event
- `GET /api/events/{id}` - Get event by ID
- `PUT /api/events/{id}` - Update event
- `DELETE /api/events/{id}` - Delete event
- `GET /api/events/published` - Get published events
- `GET /api/events/search` - Search events

### Event Approval
- `POST /api/event-approvals` - Create approval
- `GET /api/event-approvals/event/{eventId}` - Get approvals by event
- `GET /api/event-approvals/admin/{adminId}` - Get approvals by admin
- `PUT /api/event-approvals/{id}` - Update approval

## Configuration

- **Server Port**: 8081
- **Database**: PostgreSQL on localhost:5433
- **Database Name**: management-service
- **JPA DDL**: update (auto-creates/updates tables)
- **SQL Logging**: Enabled for debugging

## Security Considerations

- Input validation using Jakarta Validation
- Exception handling for error responses
- CORS enabled for cross-origin requests
- Transaction management for data consistency

## Performance Features

- Lazy loading for entity relationships
- Custom queries for optimized data access
- Pagination support in repositories
- Connection pooling through HikariCP

## Future Enhancements

- Authentication and Authorization (Spring Security)
- DTOs for API response optimization
- Caching layer (Redis)
- Message queue for async processing
- Monitoring and logging (Micrometer, SLF4J)
