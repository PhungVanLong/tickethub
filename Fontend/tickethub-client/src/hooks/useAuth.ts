import { useState, useEffect, useCallback } from 'react';
import { identityService } from '../services/identityService';
import type { User, LoginRequest, RegisterRequest, UpdateUserRequest } from '../services/identityService';

interface AuthState {
  user: User | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  error: string | null;
}

export const useAuth = () => {
  const [authState, setAuthState] = useState<AuthState>({
    user: null,
    isLoading: true,
    isAuthenticated: false,
    error: null,
  });

  const setLoading = (isLoading: boolean) => {
    setAuthState(prev => ({ ...prev, isLoading }));
  };

  const setError = (error: string | null) => {
    setAuthState(prev => ({ ...prev, error }));
  };

  const setUser = (user: User | null) => {
    setAuthState(prev => ({
      ...prev,
      user,
      isAuthenticated: !!user,
    }));
  };

  const initializeAuth = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);

      if (identityService.isAuthenticated()) {
        const user = await identityService.getCurrentUser();
        setUser(user);
      } else {
        setUser(null);
      }
    } catch (error) {
      console.error('Failed to initialize auth:', error);
      setError(error instanceof Error ? error.message : 'Authentication failed');
      setUser(null);
    } finally {
      setLoading(false);
    }
  }, []);

  const login = useCallback(async (credentials: LoginRequest) => {
    try {
      setLoading(true);
      setError(null);

      await identityService.login(credentials);
      const user = await identityService.getCurrentUser();
      setUser(user);
      
      return { success: true, user };
    } catch (error) {
      const errorMsg = error instanceof Error ? error.message : 'Login failed';
      setError(errorMsg);
      return { success: false, error: errorMsg };
    } finally {
      setLoading(false);
    }
  }, []);

  const register = useCallback(async (userData: RegisterRequest) => {
    try {
      setLoading(true);
      setError(null);

      await identityService.register(userData);
      
      return { success: true };
    } catch (error) {
      const errorMsg = error instanceof Error ? error.message : 'Registration failed';
      setError(errorMsg);
      return { success: false, error: errorMsg };
    } finally {
      setLoading(false);
    }
  }, []);

  const logout = useCallback(async () => {
    try {
      setLoading(true);
      await identityService.logout();
      setUser(null);
      setError(null);
      return { success: true };
    } catch (error) {
      console.error('Logout error:', error);
      setUser(null);
      setError(null);
      return { success: true };
    } finally {
      setLoading(false);
    }
  }, []);

  const refreshAccessToken = useCallback(async () => {
    try {
      await identityService.refreshToken();
      return true;
    } catch (error) {
      console.error('Token refresh failed:', error);
      await logout();
      return false;
    }
  }, [logout]);

  const updateProfile = useCallback(async (userData: UpdateUserRequest) => {
    try {
      setLoading(true);
      setError(null);

      const user = await identityService.updateCurrentUser(userData);
      setUser(user);
      return { success: true, user };
    } catch (error) {
      const errorMsg = error instanceof Error ? error.message : 'Profile update failed';
      setError(errorMsg);
      return { success: false, error: errorMsg };
    } finally {
      setLoading(false);
    }
  }, []);

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  useEffect(() => {
    initializeAuth();
  }, [initializeAuth]);

  return {
    ...authState,
    login,
    register,
    logout,
    refreshAccessToken,
    updateProfile,
    clearError,
    initializeAuth,
  };
};
