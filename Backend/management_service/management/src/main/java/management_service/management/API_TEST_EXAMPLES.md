# API Testing Examples - Complete Guide

## 🧪 Testing All Endpoints

### Base URL
```
http://localhost:8081
```

---

## 👥 USER MANAGEMENT ENDPOINTS

### 1. Create User
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "role": "ORGANIZER"
  }'
```

### 2. Create Admin User
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Admin User",
    "email": "admin@example.com",
    "role": "ADMIN"
  }'
```

### 3. Create Staff User
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Staff Member",
    "email": "staff@example.com",
    "role": "STAFF"
  }'
```

### 4. Get All Users
```bash
curl -X GET http://localhost:8081/api/users
```

### 5. Get User by ID
```bash
curl -X GET http://localhost:8081/api/users/1
```

### 6. Get User by Email
```bash
curl -X GET http://localhost:8081/api/users/email/john.doe@example.com
```

### 7. Get Users by Role
```bash
curl -X GET http://localhost:8081/api/users/role/ORGANIZER
```

### 8. Update User
```bash
curl -X PUT http://localhost:8081/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Updated",
    "email": "john.updated@example.com",
    "role": "ORGANIZER"
  }'
```

### 9. Get User Count by Role
```bash
curl -X GET http://localhost:8081/api/users/count/ORGANIZER
```

### 10. Delete User
```bash
curl -X DELETE http://localhost:8081/api/users/3
```

---

## 🎭 EVENT MANAGEMENT ENDPOINTS

### 1. Create Event
```bash
curl -X POST "http://localhost:8081/api/events?organizerId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tech Conference 2024",
    "description": "Annual technology conference featuring latest innovations",
    "venue": "Convention Center",
    "city": "San Francisco",
    "locationCoords": "37.7749,-122.4194",
    "startTime": "2024-06-01T09:00:00",
    "endTime": "2024-06-01T18:00:00",
    "bannerUrl": "https://example.com/banner.jpg",
    "isPublished": false
  }'
```

### 2. Create Another Event
```bash
curl -X POST "http://localhost:8081/api/events?organizerId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Music Festival",
    "description": "Summer music festival with multiple artists",
    "venue": "Central Park",
    "city": "New York",
    "locationCoords": "40.7829,-73.9654",
    "startTime": "2024-07-15T12:00:00",
    "endTime": "2024-07-15T23:00:00",
    "bannerUrl": "https://example.com/music-banner.jpg",
    "isPublished": false
  }'
```

### 3. Get All Events
```bash
curl -X GET http://localhost:8081/api/events
```

### 4. Get Event by ID
```bash
curl -X GET http://localhost:8081/api/events/1
```

### 5. Get Events by Organizer
```bash
curl -X GET http://localhost:8081/api/events/organizer/1
```

### 6. Get Events by Status
```bash
curl -X GET http://localhost:8081/api/events/status/DRAFT
```

### 7. Get Events by City
```bash
curl -X GET http://localhost:8081/api/events/city/San%20Francisco
```

### 8. Get Published Events
```bash
curl -X GET http://localhost:8081/api/events/published
```

### 9. Search Events by Title
```bash
curl -X GET "http://localhost:8081/api/events/search?title=Tech"
```

### 10. Get Events Between Dates
```bash
curl -X GET "http://localhost:8081/api/events/between-dates?start=2024-06-01T00:00:00&end=2024-07-31T23:59:59"
```

### 11. Update Event
```bash
curl -X PUT http://localhost:8081/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tech Conference 2024 - Updated",
    "description": "Annual technology conference featuring latest innovations - Updated",
    "venue": "Convention Center - Hall A",
    "city": "San Francisco",
    "locationCoords": "37.7749,-122.4194",
    "startTime": "2024-06-01T09:00:00",
    "endTime": "2024-06-01T18:00:00",
    "bannerUrl": "https://example.com/banner-updated.jpg",
    "isPublished": false
  }'
```

### 12. Update Event Status
```bash
curl -X PUT "http://localhost:8081/api/events/1/status?status=PENDING_APPROVAL"
```

### 13. Publish Event
```bash
curl -X PUT http://localhost:8081/api/events/1/publish
```

### 14. Delete Event
```bash
curl -X DELETE http://localhost:8081/api/events/2
```

---

## ✅ EVENT APPROVAL ENDPOINTS

### 1. Approve Event
```bash
curl -X POST "http://localhost:8081/api/event-approvals?eventId=1&adminId=2&decision=APPROVED&reason=Event meets all requirements"
```

### 2. Reject Event
```bash
curl -X POST "http://localhost:8081/api/event-approvals?eventId=1&adminId=2&decision=REJECTED&reason=Venue capacity insufficient"
```

### 3. Get Approval by ID
```bash
curl -X GET http://localhost:8081/api/event-approvals/1
```

### 4. Get Approvals by Event
```bash
curl -X GET http://localhost:8081/api/event-approvals/event/1
```

### 5. Get Approvals by Admin
```bash
curl -X GET http://localhost:8081/api/event-approvals/admin/2
```

### 6. Get Approvals by Decision
```bash
curl -X GET http://localhost:8081/api/event-approvals/decision/APPROVED
```

### 7. Update Approval
```bash
curl -X PUT "http://localhost:8081/api/event-approvals/1?decision=APPROVED&reason=Re-evaluated and approved"
```

### 8. Get Approval Count by Event
```bash
curl -X GET http://localhost:8081/api/event-approvals/count/1
```

### 9. Delete Approval
```bash
curl -X DELETE http://localhost:8081/api/event-approvals/1
```

---

## 🔄 Complete Workflow Example

### Step 1: Create Users
```bash
# Create Organizer
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Event Organizer","email":"organizer@events.com","role":"ORGANIZER"}'

# Create Admin
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Admin User","email":"admin@events.com","role":"ADMIN"}'
```

### Step 2: Create Event
```bash
curl -X POST "http://localhost:8081/api/events?organizerId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Annual Tech Summit",
    "description": "Premier technology conference",
    "venue": "Tech Center",
    "city": "Seattle",
    "startTime": "2024-08-15T09:00:00",
    "endTime": "2024-08-15T17:00:00",
    "isPublished": false
  }'
```

### Step 3: Approve Event
```bash
curl -X POST "http://localhost:8081/api/event-approvals?eventId=1&adminId=2&decision=APPROVED&reason=Event approved for publication"
```

### Step 4: Publish Event
```bash
curl -X PUT http://localhost:8081/api/events/1/publish
```

### Step 5: Verify Published Event
```bash
curl -X GET http://localhost:8081/api/events/published
```

---

## 🧪 Advanced Testing Scenarios

### Test Error Handling
```bash
# Test invalid user ID
curl -X GET http://localhost:8081/api/users/999

# Test duplicate email
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Duplicate","email":"organizer@events.com","role":"USER"}'

# Test publishing non-approved event
curl -X PUT http://localhost:8081/api/events/2/publish
```

### Test Search Functionality
```bash
# Search events
curl -X GET "http://localhost:8081/api/events/search?title=Tech"

# Filter by city
curl -X GET http://localhost:8081/api/events/city/Seattle

# Filter by status
curl -X GET http://localhost:8081/api/events/status/APPROVED
```

### Test Date Range Queries
```bash
curl -X GET "http://localhost:8081/api/events/between-dates?start=2024-01-01T00:00:00&end=2024-12-31T23:59:59"
```

---

## 📊 Expected Response Formats

### User Response
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "role": "ORGANIZER",
  "syncedAt": null,
  "events": [],
  "eventApprovals": [],
  "eventStaff": []
}
```

### Event Response
```json
{
  "id": 1,
  "organizer": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "role": "ORGANIZER"
  },
  "title": "Tech Conference 2024",
  "description": "Annual technology conference",
  "venue": "Convention Center",
  "city": "San Francisco",
  "locationCoords": "37.7749,-122.4194",
  "startTime": "2024-06-01T09:00:00",
  "endTime": "2024-06-01T18:00:00",
  "bannerUrl": "https://example.com/banner.jpg",
  "status": "DRAFT",
  "isPublished": false,
  "createdAt": "2024-03-30T10:00:00",
  "updatedAt": "2024-03-30T10:00:00"
}
```

### Event Approval Response
```json
{
  "id": 1,
  "event": {
    "id": 1,
    "title": "Tech Conference 2024"
  },
  "admin": {
    "id": 2,
    "fullName": "Admin User",
    "email": "admin@example.com"
  },
  "decision": "APPROVED",
  "reason": "Event meets all requirements",
  "decidedAt": "2024-03-30T10:30:00"
}
```

---

## 🔧 Testing Tools

### Using HTTPie (Alternative to curl)
```bash
# Install HTTPie: pip install httpie

# Create user
http POST localhost:8081/api/users \
  fullName="John Doe" \
  email="john@example.com" \
  role="ORGANIZER"

# Get all users
http GET localhost:8081/api/users
```

### Using Postman
1. Import collection with base URL: `http://localhost:8081`
2. Set Content-Type header to `application/json`
3. Use the examples above for each endpoint

### Using PowerShell
```powershell
# Create user
Invoke-RestMethod -Uri "http://localhost:8081/api/users" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"fullName":"John Doe","email":"john@example.com","role":"ORGANIZER"}'
```

---

## 🚀 Performance Testing

### Load Testing with Apache Bench
```bash
# Test user endpoint
ab -n 1000 -c 10 http://localhost:8081/api/users

# Test events endpoint
ab -n 500 -c 5 http://localhost:8081/api/events
```

### Concurrent Testing
```bash
# Multiple concurrent requests
for i in {1..10}; do
  curl -X GET http://localhost:8081/api/users &
done
wait
```

---

## ✅ Success Criteria

### Expected Results
- **200 OK**: Successful GET/PUT/DELETE operations
- **201 Created**: Successful POST operations
- **400 Bad Request**: Invalid input data
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server errors

### Database Verification
```sql
-- Check users
SELECT * FROM users_ref;

-- Check events
SELECT * FROM events;

-- Check event approvals
SELECT * FROM event_approvals;

-- Check relationships
SELECT u.full_name, e.title FROM users_ref u 
JOIN events e ON u.id = e.organizer_id;
```

---

**🎉 All endpoints are ready for testing! Run these examples to verify the complete system functionality.**
