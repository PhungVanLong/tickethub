# Identity Service Documentation

This service provides a complete authentication and user management solution for the frontend application.

## Features

- User registration and login
- JWT token management (access & refresh tokens)
- Automatic token refresh
- User profile management
- Admin user lookup by ID
- TypeScript support with full type safety

## Usage

### 1. IdentityService Class

The `IdentityService` class provides direct API methods:

```typescript
import { identityService } from '../services';

// Register a new user
const registerResponse = await identityService.register({
  email: 'user@example.com',
  password: 'password123',
  firstName: 'John',
  lastName: 'Doe'
});

// Login
const loginResponse = await identityService.login({
  email: 'user@example.com',
  password: 'password123'
});

// Get current user
const userResponse = await identityService.getCurrentUser();

// Update user profile
const updateResponse = await identityService.updateCurrentUser({
  firstName: 'John Updated'
});

// Logout
await identityService.logout();
```

### 2. useAuth Hook

The `useAuth` hook provides reactive state management:

```typescript
import { useAuth } from '../hooks';

function MyComponent() {
  const {
    user,
    isLoading,
    isAuthenticated,
    error,
    login,
    register,
    logout,
    updateProfile,
    clearError
  } = useAuth();

  const handleLogin = async () => {
    const result = await login({
      email: 'user@example.com',
      password: 'password123'
    });
    
    if (result.success) {
      console.log('Login successful:', result.user);
    } else {
      console.error('Login failed:', result.error);
    }
  };

  // Component logic...
}
```

## API Endpoints

All endpoints are called with the base URL `http://localhost:8080`:

- `POST /auth/register` - Create new account
- `POST /auth/login` - User login
- `POST /auth/refresh` - Refresh access token
- `POST /auth/logout` - User logout
- `GET /users/me` - Get current user info
- `PUT /users/me` - Update current user info
- `GET /users/{id}` - Get user by ID (admin only)

## Token Management

- Access tokens are stored in `localStorage` under key `access_token`
- Refresh tokens are stored in `localStorage` under key `refresh_token`
- Tokens are automatically included in API requests via `Authorization: Bearer <token>` header
- Token refresh is handled automatically when needed

## Error Handling

All methods throw errors with descriptive messages. The `useAuth` hook catches these errors and provides them in the `error` state.

## TypeScript Types

```typescript
interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  role?: string;
  createdAt?: string;
  updatedAt?: string;
}

interface LoginRequest {
  email: string;
  password: string;
}

interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

interface UpdateUserRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
}
```

## Example Component

See `AuthExample.tsx` for a complete working example of how to implement authentication in a React component.
