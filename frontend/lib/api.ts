const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

export interface AuthResponse {
  token: string;
  username: string;
  email: string;
  role: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

class ApiClient {
  private getHeaders(): HeadersInit {
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    };

    const token = localStorage.getItem('token');
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    return headers;
  }

  async request<T>(endpoint: string, options?: RequestInit): Promise<T> {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      ...options,
      headers: {
        ...this.getHeaders(),
        ...options?.headers,
      },
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || 'API request failed');
    }

    return response.json();
  }

  // Auth endpoints
  async register(data: RegisterRequest): Promise<AuthResponse> {
    return this.request<AuthResponse>('/auth/register', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async login(data: LoginRequest): Promise<AuthResponse> {
    return this.request<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  // User endpoints
  async getCurrentUser(): Promise<any> {
    return this.request('/users/me');
  }

  async getAllUsers(): Promise<any[]> {
    return this.request('/users');
  }

  async updateUserRole(userId: number, role: string): Promise<any> {
    return this.request(`/users/${userId}/role?role=${role}`, {
      method: 'PUT',
    });
  }

  async toggleUserActive(userId: number): Promise<any> {
    return this.request(`/users/${userId}/toggle-active`, {
      method: 'PUT',
    });
  }

  // Profile endpoints
  async getMyProfile(): Promise<any> {
    return this.request('/profiles/me');
  }

  async getAllProfiles(): Promise<any[]> {
    return this.request('/profiles');
  }

  async updateProfile(profileId: number, data: any): Promise<any> {
    return this.request(`/profiles/${profileId}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  }

  // House endpoints
  async getMyHouses(): Promise<any[]> {
    return this.request('/houses/me');
  }

  async getAllHouses(): Promise<any[]> {
    return this.request('/houses');
  }

  async createHouse(data: any): Promise<any> {
    return this.request('/houses', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async updateHouse(houseId: number, data: any): Promise<any> {
    return this.request(`/houses/${houseId}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  }

  async deleteHouse(houseId: number): Promise<void> {
    return this.request(`/houses/${houseId}`, {
      method: 'DELETE',
    });
  }

  // Vehicle endpoints
  async getMyVehicles(): Promise<any[]> {
    return this.request('/vehicles/me');
  }

  async getAllVehicles(): Promise<any[]> {
    return this.request('/vehicles');
  }

  async createVehicle(data: any): Promise<any> {
    return this.request('/vehicles', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async updateVehicle(vehicleId: number, data: any): Promise<any> {
    return this.request(`/vehicles/${vehicleId}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  }

  async deleteVehicle(vehicleId: number): Promise<void> {
    return this.request(`/vehicles/${vehicleId}`, {
      method: 'DELETE',
    });
  }

  // Ban endpoints
  async getAllBans(): Promise<any[]> {
    return this.request('/admin/bans');
  }

  async getBansByUserId(userId: number): Promise<any[]> {
    return this.request(`/admin/bans/user/${userId}`);
  }

  async createBan(data: any): Promise<any> {
    return this.request('/admin/bans', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async deactivateBan(banId: number): Promise<any> {
    return this.request(`/admin/bans/${banId}/deactivate`, {
      method: 'PUT',
    });
  }

  // Warning endpoints
  async getAllWarnings(): Promise<any[]> {
    return this.request('/admin/warnings');
  }

  async getWarningsByUserId(userId: number): Promise<any[]> {
    return this.request(`/admin/warnings/user/${userId}`);
  }

  async createWarning(data: any): Promise<any> {
    return this.request('/admin/warnings', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  // Admin Note endpoints
  async getAllNotes(): Promise<any[]> {
    return this.request('/admin/notes');
  }

  async getNotesByUserId(userId: number): Promise<any[]> {
    return this.request(`/admin/notes/user/${userId}`);
  }

  async createNote(data: any): Promise<any> {
    return this.request('/admin/notes', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }
}

export const apiClient = new ApiClient();
