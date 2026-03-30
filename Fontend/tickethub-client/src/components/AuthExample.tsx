import React, { useState } from 'react';
import { useAuth } from '../hooks';
import type { LoginRequest, RegisterRequest } from '../services';

export const AuthExample: React.FC = () => {
  const { user, isLoading, isAuthenticated, error, login, register, logout } = useAuth();
  
  const [loginForm, setLoginForm] = useState<LoginRequest>({
    email: '',
    password: '',
  });
  
  const [registerForm, setRegisterForm] = useState<RegisterRequest>({
    email: '',
    password: '',
    firstName: '',
    lastName: '',
  });

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    await login(loginForm);
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    await register(registerForm);
  };

  const handleLogout = async () => {
    await logout();
  };

  if (isLoading) {
    return <div className="flex justify-center items-center p-8">Loading...</div>;
  }

  if (isAuthenticated && user) {
    return (
      <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
        <h2 className="text-2xl font-bold mb-4">Welcome, {user.firstName}!</h2>
        <div className="space-y-2 mb-4">
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Name:</strong> {user.firstName} {user.lastName}</p>
          <p><strong>ID:</strong> {user.id}</p>
        </div>
        <button
          onClick={handleLogout}
          className="w-full bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600 transition-colors"
        >
          Logout
        </button>
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-center">Authentication</h2>
      
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <div className="space-y-6">
        <div>
          <h3 className="text-lg font-semibold mb-3">Login</h3>
          <form onSubmit={handleLogin} className="space-y-3">
            <input
              type="email"
              placeholder="Email"
              value={loginForm.email}
              onChange={(e) => setLoginForm({ ...loginForm, email: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={loginForm.password}
              onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 transition-colors"
            >
              Login
            </button>
          </form>
        </div>

        <div>
          <h3 className="text-lg font-semibold mb-3">Register</h3>
          <form onSubmit={handleRegister} className="space-y-3">
            <input
              type="text"
              placeholder="First Name"
              value={registerForm.firstName}
              onChange={(e) => setRegisterForm({ ...registerForm, firstName: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
            <input
              type="text"
              placeholder="Last Name"
              value={registerForm.lastName}
              onChange={(e) => setRegisterForm({ ...registerForm, lastName: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
            <input
              type="email"
              placeholder="Email"
              value={registerForm.email}
              onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={registerForm.password}
              onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })}
              className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
            <button
              type="submit"
              className="w-full bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600 transition-colors"
            >
              Register
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
