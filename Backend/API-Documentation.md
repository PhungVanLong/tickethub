# Tickethub API Documentation

## Overview

This document provides comprehensive API endpoint documentation for the Tickethub microservices architecture. All requests go through the API Gateway at `http://localhost:8080`.

## Base URL
```
http://localhost:8080
```

## Authentication

### JWT Token Authentication
- **Type**: Bearer Token
- **Header**: `Authorization: Bearer <access_token>`
- **Token Expiry**: 1 hour (3600000ms)
- **Refresh Token**: Available for token renewal

### Test Accounts

#### Admin Account
```json
{
  "email": "admin@tickethub.com",
  "password": "password",
  "role": "ROLE_ADMIN"
}
```

#### Customer Account
```json
{
  "email": "testuser@new.com", 
  "password": "12345678",
  "role": "ROLE_CUSTOMER"
}
```

---

## 🔐 Authentication Service Endpoints

### 1. User Registration
```http
POST /api/auth/register
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "User Name",
  "phone": "0123456789",
  "avatarUrl": "https://example.com/avatar.jpg",
  "captchaToken": "dummy-token",
  "captchaPassed": true,
  "captchaScore": 0.9
}
```

**Response (201):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

### 2. User Login
```http
POST /api/auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

### 3. Refresh Token
```http
POST /api/auth/refresh
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Response (200):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

### 4. Logout
```http
POST /api/auth/logout
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "message": "Logged out successfully"
}
```

---

## 👥 User Management Endpoints

### 1. Get Current User Profile
```http
GET /api/users/profile
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "fullName": "User Name",
  "phone": "0123456789",
  "avatarUrl": "https://example.com/avatar.jpg",
  "role": "ROLE_CUSTOMER",
  "isActive": true,
  "isVerified": true,
  "createdAt": "2026-03-31T04:25:41.840094",
  "updatedAt": "2026-03-31T04:25:41.840094"
}
```

### 2. Update User Profile
```http
PUT /api/users/profile
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "fullName": "Updated Name",
  "phone": "0987654321",
  "avatarUrl": "https://example.com/new-avatar.jpg"
}
```

**Response (200):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "fullName": "Updated Name",
  "phone": "0987654321",
  "avatarUrl": "https://example.com/new-avatar.jpg",
  "role": "ROLE_CUSTOMER",
  "isActive": true,
  "isVerified": true,
  "createdAt": "2026-03-31T04:25:41.840094",
  "updatedAt": "2026-03-31T04:30:00.000000"
}
```

### 3. Change Password
```http
PUT /api/users/password
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "currentPassword": "oldPassword123",
  "newPassword": "newPassword123"
}
```

**Response (200):**
```json
{
  "message": "Password changed successfully"
}
```

---

## 🎫 Event Management Endpoints

### 1. Get All Events
```http
GET /api/events
```

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 10)
- `category` (optional): Filter by category
- `status` (optional): Filter by status (ACTIVE, UPCOMING, COMPLETED, CANCELLED)

**Response (200):**
```json
{
  "content": [
    {
      "id": "event-uuid-123",
      "title": "Summer Music Festival",
      "description": "Amazing music festival with top artists",
      "category": "MUSIC",
      "status": "ACTIVE",
      "startDate": "2026-06-15T18:00:00",
      "endDate": "2026-06-15T23:00:00",
      "venue": {
        "name": "Central Park Arena",
        "address": "123 Main St, City",
        "capacity": 5000
      },
      "ticketPrice": 75000,
      "availableTickets": 3500,
      "totalTickets": 5000,
      "imageUrl": "https://example.com/event-image.jpg",
      "createdAt": "2026-03-31T04:00:00",
      "updatedAt": "2026-03-31T04:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### 2. Get Event Details
```http
GET /api/events/{eventId}
```

**Response (200):**
```json
{
  "id": "event-uuid-123",
  "title": "Summer Music Festival",
  "description": "Amazing music festival with top artists",
  "category": "MUSIC",
  "status": "ACTIVE",
  "startDate": "2026-06-15T18:00:00",
  "endDate": "2026-06-15T23:00:00",
  "venue": {
    "name": "Central Park Arena",
    "address": "123 Main St, City",
    "capacity": 5000
  },
  "ticketPrice": 75000,
  "availableTickets": 3500,
  "totalTickets": 5000,
  "imageUrl": "https://example.com/event-image.jpg",
  "organizer": {
    "id": "organizer-uuid",
    "name": "Event Organizer Co.",
    "email": "organizer@example.com"
  },
  "createdAt": "2026-03-31T04:00:00",
  "updatedAt": "2026-03-31T04:00:00"
}
```

### 3. Search Events
```http
GET /api/events/search
```

**Query Parameters:**
- `q` (required): Search query
- `category` (optional): Filter by category
- `minPrice` (optional): Minimum ticket price
- `maxPrice` (optional): Maximum ticket price
- `startDate` (optional): Filter by start date (ISO format)
- `endDate` (optional): Filter by end date (ISO format)

**Response (200):** Same as Get All Events

---

## 🎟️ Ticket Management Endpoints

### 1. Get Available Tickets
```http
GET /api/tickets/available
```

**Query Parameters:**
- `eventId` (required): Event ID
- `quantity` (optional): Number of tickets needed (default: 1)

**Response (200):**
```json
{
  "eventId": "event-uuid-123",
  "availableTickets": 3500,
  "ticketTypes": [
    {
      "type": "GENERAL",
      "price": 75000,
      "available": 2000
    },
    {
      "type": "VIP",
      "price": 150000,
      "available": 500
    },
    {
      "type": "PREMIUM",
      "price": 300000,
      "available": 1000
    }
  ]
}
```

### 2. Create Ticket Order
```http
POST /api/orders
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "eventId": "event-uuid-123",
  "tickets": [
    {
      "type": "GENERAL",
      "quantity": 2
    },
    {
      "type": "VIP", 
      "quantity": 1
    }
  ]
}
```

**Response (201):**
```json
{
  "orderId": "order-uuid-123",
  "eventId": "event-uuid-123",
  "userId": "user-uuid-123",
  "status": "PENDING",
  "tickets": [
    {
      "id": "ticket-uuid-1",
      "type": "GENERAL",
      "price": 75000,
      "quantity": 2
    },
    {
      "id": "ticket-uuid-2",
      "type": "VIP",
      "price": 150000,
      "quantity": 1
    }
  ],
  "totalAmount": 300000,
  "createdAt": "2026-03-31T04:00:00",
  "expiresAt": "2026-03-31T04:15:00"
}
```

### 3. Get Order Details
```http
GET /api/orders/{orderId}
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "orderId": "order-uuid-123",
  "eventId": "event-uuid-123",
  "userId": "user-uuid-123",
  "status": "CONFIRMED",
  "event": {
    "id": "event-uuid-123",
    "title": "Summer Music Festival",
    "startDate": "2026-06-15T18:00:00",
    "venue": {
      "name": "Central Park Arena",
      "address": "123 Main St, City"
    }
  },
  "tickets": [
    {
      "id": "ticket-uuid-1",
      "type": "GENERAL",
      "price": 75000,
      "quantity": 2,
      "seatNumbers": ["A1", "A2"]
    },
    {
      "id": "ticket-uuid-2",
      "type": "VIP",
      "price": 150000,
      "quantity": 1,
      "seatNumbers": ["B1"]
    }
  ],
  "totalAmount": 300000,
  "paymentStatus": "PAID",
  "createdAt": "2026-03-31T04:00:00",
  "confirmedAt": "2026-03-31T04:05:00"
}
```

### 4. Get User Orders
```http
GET /api/orders/user
Authorization: Bearer <access_token>
```

**Query Parameters:**
- `status` (optional): Filter by status (PENDING, CONFIRMED, CANCELLED, COMPLETED)
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 10)

**Response (200):** Paginated list of orders

---

## 💳 Payment Endpoints

### 1. Create Payment
```http
POST /api/payments
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "orderId": "order-uuid-123",
  "paymentMethod": "CREDIT_CARD",
  "amount": 300000
}
```

**Response (201):**
```json
{
  "paymentId": "payment-uuid-123",
  "orderId": "order-uuid-123",
  "amount": 300000,
  "status": "PENDING",
  "paymentMethod": "CREDIT_CARD",
  "paymentUrl": "https://payment-gateway.com/pay/payment-uuid-123",
  "expiresAt": "2026-03-31T04:15:00",
  "createdAt": "2026-03-31T04:00:00"
}
```

### 2. Get Payment Status
```http
GET /api/payments/{paymentId}
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "paymentId": "payment-uuid-123",
  "orderId": "order-uuid-123",
  "amount": 300000,
  "status": "COMPLETED",
  "paymentMethod": "CREDIT_CARD",
  "transactionId": "txn-123456",
  "paidAt": "2026-03-31T04:05:00",
  "createdAt": "2026-03-31T04:00:00"
}
```

---

## 📊 Analytics Endpoints (Admin Only)

### 1. Get Dashboard Statistics
```http
GET /api/analytics/dashboard
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "totalUsers": 1250,
  "totalEvents": 45,
  "totalOrders": 3200,
  "totalRevenue": 25000000,
  "monthlyRevenue": [
    {
      "month": "2026-01",
      "revenue": 5000000
    },
    {
      "month": "2026-02", 
      "revenue": 7500000
    },
    {
      "month": "2026-03",
      "revenue": 12500000
    }
  ],
  "topEvents": [
    {
      "eventId": "event-uuid-1",
      "title": "Summer Music Festival",
      "ticketsSold": 4500,
      "revenue": 15000000
    }
  ]
}
```

### 2. Get Event Analytics
```http
GET /api/analytics/events/{eventId}
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "eventId": "event-uuid-123",
  "totalTicketsSold": 4500,
  "totalRevenue": 15000000,
  "ticketSalesByType": [
    {
      "type": "GENERAL",
      "sold": 3000,
      "revenue": 75000000
    },
    {
      "type": "VIP",
      "sold": 1000,
      "revenue": 50000000
    },
    {
      "type": "PREMIUM",
      "sold": 500,
      "revenue": 75000000
    }
  ],
  "dailySales": [
    {
      "date": "2026-03-30",
      "ticketsSold": 150,
      "revenue": 500000
    }
  ]
}
```

---

## 🔔 Notification Endpoints

### 1. Get User Notifications
```http
GET /api/notifications
Authorization: Bearer <access_token>
```

**Query Parameters:**
- `read` (optional): Filter by read status (true/false)
- `type` (optional): Filter by type (ORDER, PAYMENT, EVENT, SYSTEM)

**Response (200):**
```json
{
  "content": [
    {
      "id": "notif-uuid-1",
      "title": "Order Confirmed",
      "message": "Your order #12345 has been confirmed",
      "type": "ORDER",
      "isRead": false,
      "createdAt": "2026-03-31T04:00:00"
    }
  ],
  "unreadCount": 3
}
```

### 2. Mark Notification as Read
```http
PUT /api/notifications/{notificationId}/read
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "message": "Notification marked as read"
}
```

---

## 🛡️ Platform Configuration (Admin Only)

### 1. Get System Settings
```http
GET /api/platform-config
Authorization: Bearer <access_token>
```

**Response (200):**
```json
{
  "system": {
    "maintenance": false,
    "registrationEnabled": true,
    "maxTicketsPerOrder": 10,
    "paymentTimeoutMinutes": 15
  },
  "features": {
    "eventCreation": true,
    "ticketResale": true,
    "multiLanguage": true
  },
  "limits": {
    "maxFileSize": 5242880,
    "maxEventsPerUser": 50
  }
}
```

### 2. Update System Settings
```http
PUT /api/platform-config
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "system": {
    "maintenance": false,
    "registrationEnabled": true,
    "maxTicketsPerOrder": 10,
    "paymentTimeoutMinutes": 15
  },
  "features": {
    "eventCreation": true,
    "ticketResale": true,
    "multiLanguage": true
  }
}
```

---

## 📝 Error Responses

### Standard Error Format
```json
{
  "timestamp": "2026-03-31T04:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/auth/login",
  "errors": [
    {
      "field": "email",
      "message": "Email is required"
    }
  ]
}
```

### Common HTTP Status Codes

| Status | Description | Example Scenarios |
|--------|-------------|-------------------|
| 200 | OK | Successful GET/PUT/DELETE |
| 201 | Created | Successful POST (registration, order) |
| 400 | Bad Request | Invalid request body, validation errors |
| 401 | Unauthorized | Missing/invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Duplicate resource (email already exists) |
| 422 | Unprocessable Entity | Business logic validation failed |
| 500 | Internal Server Error | System error |

---

## 🔧 Frontend Integration Guide

### 1. Authentication Flow
```javascript
// Login
const login = async (email, password) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, password })
  });
  
  const data = await response.json();
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  return data;
};

// Auto-refresh token
const refreshToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken');
  const response = await fetch('http://localhost:8080/api/auth/refresh', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ refreshToken })
  });
  
  const data = await response.json();
  localStorage.setItem('accessToken', data.accessToken);
  return data.accessToken;
};

// API call with auth
const apiCall = async (url, options = {}) => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json',
    ...options.headers
  };
  
  try {
    const response = await fetch(`http://localhost:8080${url}`, {
      ...options,
      headers
    });
    
    if (response.status === 401) {
      // Try to refresh token
      await refreshToken();
      // Retry with new token
      return apiCall(url, options);
    }
    
    return response.json();
  } catch (error) {
    console.error('API call failed:', error);
    throw error;
  }
};
```

### 2. CORS Configuration
The API Gateway is configured to allow all origins for development:
```yaml
allowedOrigins: "*"
allowedMethods: "*"
allowedHeaders: "*"
```

### 3. Pagination
All list endpoints support pagination:
```javascript
const getEvents = async (page = 0, size = 10) => {
  return apiCall(`/api/events?page=${page}&size=${size}`);
};
```

### 4. Error Handling
```javascript
const handleApiError = (error) => {
  if (error.status === 401) {
    // Redirect to login
    window.location.href = '/login';
  } else if (error.status === 403) {
    // Show access denied message
    alert('Access denied');
  } else if (error.status >= 500) {
    // Show server error message
    alert('Server error. Please try again later.');
  } else {
    // Show validation errors
    alert(error.message || 'An error occurred');
  }
};
```

---

## 🚀 Quick Start for Frontend

### 1. Install Dependencies
```bash
npm install axios
```

### 2. Create API Service
```javascript
// api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

class ApiService {
  constructor() {
    this.client = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    // Request interceptor for auth token
    this.client.interceptors.request.use((config) => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });
    
    // Response interceptor for token refresh
    this.client.interceptors.response.use(
      (response) => response,
      async (error) => {
        if (error.response?.status === 401) {
          try {
            const refreshToken = localStorage.getItem('refreshToken');
            const response = await axios.post(`${API_BASE_URL}/api/auth/refresh`, {
              refreshToken
            });
            
            const { accessToken } = response.data;
            localStorage.setItem('accessToken', accessToken);
            
            // Retry original request
            error.config.headers.Authorization = `Bearer ${accessToken}`;
            return this.client.request(error.config);
          } catch (refreshError) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            window.location.href = '/login';
          }
        }
        return Promise.reject(error);
      }
    );
  }
  
  // Auth methods
  async login(email, password) {
    const response = await this.client.post('/api/auth/login', {
      email,
      password
    });
    return response.data;
  }
  
  async register(userData) {
    const response = await this.client.post('/api/auth/register', userData);
    return response.data;
  }
  
  // Event methods
  async getEvents(params = {}) {
    const response = await this.client.get('/api/events', { params });
    return response.data;
  }
  
  async getEvent(eventId) {
    const response = await this.client.get(`/api/events/${eventId}`);
    return response.data;
  }
  
  // Order methods
  async createOrder(orderData) {
    const response = await this.client.post('/api/orders', orderData);
    return response.data;
  }
  
  async getOrders(params = {}) {
    const response = await this.client.get('/api/orders/user', { params });
    return response.data;
  }
}

export default new ApiService();
```

### 3. Usage Example
```javascript
// App.js
import api from './api';

// Login
const handleLogin = async () => {
  try {
    const result = await api.login('admin@tickethub.com', 'password');
    console.log('Login successful:', result);
  } catch (error) {
    console.error('Login failed:', error);
  }
};

// Get events
const loadEvents = async () => {
  try {
    const events = await api.getEvents({ page: 0, size: 10 });
    console.log('Events:', events);
  } catch (error) {
    console.error('Failed to load events:', error);
  }
};

// Create order
const createOrder = async () => {
  try {
    const order = await api.createOrder({
      eventId: 'event-uuid-123',
      tickets: [
        { type: 'GENERAL', quantity: 2 }
      ]
    });
    console.log('Order created:', order);
  } catch (error) {
    console.error('Failed to create order:', error);
  }
};
```

---

## 📞 Support

For any API-related issues or questions:
1. Check the error response messages
2. Verify authentication tokens
3. Ensure correct request format
4. Check service status via Docker containers

**All endpoints are now ready for frontend development!** 🚀
