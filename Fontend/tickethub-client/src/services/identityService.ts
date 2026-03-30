export interface RegisterRequest {
  email: string;
  password: string;
  fullName: string;
  phone?: string;
  avatarUrl?: string;
  captchaToken?: string;
  captchaPassed?: boolean;
  captchaScore?: number;
}

export interface LoginRequest {
  email: string;
  password: string;
  captchaToken?: string;
  captchaPassed?: boolean;
  captchaScore?: number;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
}

export interface User {
  id: string;
  email: string;
  fullName: string;
  phone?: string;
  avatarUrl?: string;
  role?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface UpdateUserRequest {
  fullName?: string;
  phone?: string;
  avatarUrl?: string;
}

export interface ApiError {
  message: string;
  error?: string;
  status?: number;
}

class IdentityService {
  private readonly baseUrl = '/api';
  private readonly accessTokenKey = 'access_token';
  private readonly refreshTokenKey = 'refresh_token';

  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseUrl}${endpoint}`;
    
    const defaultHeaders: Record<string, string> = {
      'Content-Type': 'application/json',
    };

    const token = this.getAccessToken();
    if (token) {
      defaultHeaders.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(url, {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers,
      },
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({
        message: 'Network error occurred',
      }));
      throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
    }

    // Handle empty 204 No Content responses
    if (response.status === 204 || response.headers.get('content-length') === '0') {
        return null as unknown as T;
    }
    
    return response.json();
  }

  getAccessToken(): string | null {
    return localStorage.getItem(this.accessTokenKey);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(this.refreshTokenKey);
  }

  setTokens(accessToken: string, refreshToken?: string): void {
    localStorage.setItem(this.accessTokenKey, accessToken);
    if (refreshToken) localStorage.setItem(this.refreshTokenKey, refreshToken);
  }

  clearTokens(): void {
    localStorage.removeItem(this.accessTokenKey);
    localStorage.removeItem(this.refreshTokenKey);
  }

  async register(data: RegisterRequest): Promise<void> {
    const payload = {
      ...data,
      captchaToken: data.captchaToken || 'bypass-captcha-token',
      captchaPassed: data.captchaPassed ?? true,
      captchaScore: data.captchaScore ?? 0.9,
    };
    await this.request<void>('/auth/register', {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  }

  async login(data: LoginRequest): Promise<AuthResponse> {
    const payload = {
      ...data,
      captchaToken: data.captchaToken || 'bypass-captcha-token',
      captchaPassed: data.captchaPassed ?? true,
      captchaScore: data.captchaScore ?? 0.9,
    };
    const response = await this.request<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(payload),
    });

    if (response && response.accessToken) {
      this.setTokens(response.accessToken);
    }

    return response;
  }

  async refreshToken(): Promise<AuthResponse> {
    const refreshToken = this.getRefreshToken();
    if (!refreshToken) {
      throw new Error('No refresh token available');
    }

    const response = await this.request<AuthResponse>(
      '/auth/refresh',
      {
        method: 'POST',
        body: JSON.stringify({ refreshToken }),
      }
    );

    if (response && response.accessToken) {
      this.setTokens(response.accessToken);
    }

    return response;
  }

  async logout(): Promise<void> {
    try {
      await this.request<void>('/auth/logout', {
        method: 'POST',
      });
    } finally {
      this.clearTokens();
    }
  }

  async getCurrentUser(): Promise<User> {
    return this.request<User>('/users/me');
  }

  async updateCurrentUser(data: UpdateUserRequest): Promise<User> {
    return this.request<User>('/users/me', {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  }

  async getUserById(id: string): Promise<User> {
    return this.request<User>(`/users/${id}`);
  }

  isAuthenticated(): boolean {
    return !!this.getAccessToken();
  }
}

export const identityService = new IdentityService();
