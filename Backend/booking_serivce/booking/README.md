# TicketHub Booking Service

## 📋 Overview

TicketHub Booking Service is a comprehensive Spring Boot application for managing event ticket bookings, payments, and user management. This service provides RESTful APIs for handling the complete ticket booking lifecycle.

## 🏗️ Architecture

The system follows a clean 3-tier architecture:

```
┌─────────────────┐
│   Controllers   │ ← REST API Layer
├─────────────────┤
│    Services    │ ← Business Logic Layer  
├─────────────────┤
│  Repositories  │ ← Data Access Layer
├─────────────────┤
│    Entities    │ ← Database Models
└─────────────────┘
```

## 🗄️ Database Schema

### Core Entities

#### **User Management**
- **users_ref**: User accounts and profiles
- **seat_holds**: Temporary seat reservations

#### **Order Processing**
- **orders**: Order management and tracking
- **order_items**: Line items within orders
- **payments**: Payment processing and status

#### **Ticket Management**
- **tickets**: Individual ticket generation and validation
- **ticket_tiers_ref**: Pricing tiers and inventory

#### **Promotion System**
- **vouchers_ref**: Discount vouchers
- **voucher_usages_ref**: Voucher usage tracking
- **ticket_promotions**: Tier-specific promotions
- **promotion_usages**: Promotion usage tracking

### Entity Relationships

```
Users_Ref (1) → (N) ORDERS
Users_Ref (1) → (N) SEAT_HOLDS
Users_Ref (1) → (N) VOUCHER_USAGES_REF

ORDERS (1) → (N) PAYMENTS
ORDERS (1) → (N) VOUCHER_USAGES_REF
ORDERS (1) → (N) ORDER_ITEMS

ORDER_ITEMS (1) → (N) TICKETS
ORDER_ITEMS (1) → (N) PROMOTION_USAGES
ORDER_ITEMS (N) → (1) TICKET_TIERS_REF

TICKET_TIERS_REF (1) → (N) TICKET_PROMOTIONS
TICKET_PROMOTIONS (1) → (N) PROMOTION_USAGES
```

## 🚀 API Endpoints

### **User Management**
```
GET    /api/users              - Get all users
GET    /api/users/{id}          - Get user by ID
GET    /api/users/username/{username} - Get user by username
GET    /api/users/email/{email}      - Get user by email
POST   /api/users              - Create new user
PUT    /api/users/{id}          - Update user
DELETE /api/users/{id}          - Delete user
PUT    /api/users/{id}/verify   - Verify user
PUT    /api/users/{id}/activate - Activate user
PUT    /api/users/{id}/deactivate - Deactivate user
```

### **Order Management**
```
GET    /api/orders              - Get all orders
GET    /api/orders/{id}          - Get order by ID
GET    /api/orders/user/{userId}  - Get orders by user
GET    /api/orders/status/{status} - Get orders by status
POST   /api/orders              - Create new order
PUT    /api/orders/{id}/status   - Update order status
```

### **Ticket Management**
```
GET    /api/tickets              - Get all tickets
GET    /api/tickets/user/{userId}  - Get tickets by user
GET    /api/tickets/status/{status} - Get tickets by status
GET    /api/tickets/validate/{ticketCode} - Validate ticket
POST   /api/tickets/use/{ticketCode}      - Use ticket
```

### **System Health**
```
GET    /api/test/health        - Health check
GET    /api/test/info          - System information
```

## 🔧 Technology Stack

- **Framework**: Spring Boot 4.0.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Library Management**: Lombok
- **Validation**: Jakarta Bean Validation
- **Logging**: SLF4J

## 🗃️ Data Transfer Objects (DTOs)

### **OrderRequest**
```java
public class OrderRequest {
    private String orderCode;
    private Long userId;
    private List<OrderItemRequest> orderItems;
    private String notes;
    
    public static class OrderItemRequest {
        private Long ticketTierId;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}
```

### **OrderResponse**
```java
public class OrderResponse {
    private Long id;
    private String orderCode;
    private Long userId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private List<OrderItemResponse> orderItems;
    private List<PaymentResponse> payments;
}
```

## 🔐 Enums

### **OrderStatus**
- `PENDING`: Order created but not confirmed
- `CONFIRMED`: Order confirmed and processing
- `CANCELLED`: Order cancelled by user or system
- `COMPLETED`: Order completed successfully

### **PaymentStatus**
- `PENDING`: Payment initiated but not processed
- `PROCESSING`: Payment being processed
- `COMPLETED`: Payment successful
- `FAILED`: Payment failed
- `REFUNDED`: Payment refunded

### **TicketStatus**
- `VALID`: Ticket is valid and ready for use
- `USED`: Ticket has been used
- `EXPIRED`: Ticket has expired
- `CANCELLED`: Ticket was cancelled

### **PromoType**
- `PERCENTAGE`: Discount as percentage
- `FIXED`: Fixed amount discount

## 🛠️ Configuration

### **Application Properties**
```properties
# Server Configuration
server.port=8086

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/booking-service
spring.datasource.username=postgres
spring.datasource.password=270504
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Connection Pool Configuration
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.maximum-pool-size=10
```

## 🔍 Business Logic Features

### **Order Processing**
- **Validation**: Check user existence, ticket availability
- **Inventory Management**: Update sold quantities
- **Status Management**: PENDING → CONFIRMED → COMPLETED
- **Cancellation**: Business rules for order cancellation

### **Ticket Management**
- **Generation**: Automatic ticket code and QR code generation
- **Validation**: Status and existence checks
- **Usage Tracking**: Mark tickets as used with timestamps
- **Expiration**: Automatic ticket expiration handling

### **User Management**
- **Authentication**: User verification and activation
- **Profile Management**: Complete CRUD operations
- **Status Management**: Active/inactive user handling

## 🚨 Error Handling

### **Global Exception Handler**
- **Validation Errors**: Structured error responses for input validation
- **Business Logic Errors**: Proper exception handling for business rules
- **System Errors**: Graceful error responses with timestamps
- **HTTP Status Codes**: Appropriate status codes for different error types

## 📊 Monitoring

### **Health Check Endpoint**
```json
{
  "status": "UP",
  "timestamp": "2026-03-30T11:19:53.158",
  "service": "Booking Service",
  "version": "1.0.0"
}
```

### **System Info Endpoint**
```json
{
  "service": "TicketHub Booking Service",
  "description": "Booking management system for events and tickets",
  "endpoints": [
    "/api/orders",
    "/api/tickets", 
    "/api/test/health"
  ]
}
```

## 🚀 Getting Started

### **Prerequisites**
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 13 or higher
- Spring Boot 4.0.5

### **Installation**
```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd booking

# Run the application
./mvnw spring-boot:run
```

### **Access Points**
- **API Base URL**: http://localhost:8086
- **Health Check**: http://localhost:8086/api/test/health
- **API Documentation**: Available at runtime

## 🧪 Testing

### **API Testing**
```bash
# Health check
curl http://localhost:8086/api/test/health

# Create user
curl -X POST http://localhost:8086/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","fullName":"Test User"}'

# Create order
curl -X POST http://localhost:8086/api/orders \
  -H "Content-Type: application/json" \
  -d '{"orderCode":"ORD-001","userId":1,"orderItems":[{"ticketTierId":1,"quantity":2,"unitPrice":50.00}]}'
```

## 📝 Development Guidelines

### **Code Standards**
- Follow Java naming conventions
- Use Lombok for boilerplate reduction
- Implement proper exception handling
- Add comprehensive logging
- Write meaningful commit messages

### **Database Best Practices**
- Use proper foreign key relationships
- Implement cascade operations
- Add appropriate indexes
- Use proper data types

## 🔄 Version History

### **v1.0.0** (Current)
- ✅ Complete entity implementation
- ✅ REST API endpoints
- ✅ Business logic layer
- ✅ Database integration
- ✅ Error handling
- ✅ Documentation

## 📞 Support

For technical support or questions:
- **Documentation**: Available in this README
- **API Examples**: Provided above
- **Health Monitoring**: Built-in health endpoints

---

**TicketHub Booking Service** - Complete ticket booking management solution 🎫
