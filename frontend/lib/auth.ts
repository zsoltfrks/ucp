import { AuthResponse } from './api';

export const saveAuth = (authData: AuthResponse) => {
  if (typeof window !== 'undefined') {
    localStorage.setItem('token', authData.token);
    localStorage.setItem('username', authData.username);
    localStorage.setItem('email', authData.email);
    localStorage.setItem('role', authData.role);
  }
};

export const clearAuth = () => {
  if (typeof window !== 'undefined') {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('email');
    localStorage.removeItem('role');
  }
};

export const getToken = (): string | null => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('token');
  }
  return null;
};

export const getRole = (): string | null => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('role');
  }
  return null;
};

export const getUsername = (): string | null => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('username');
  }
  return null;
};

export const isAuthenticated = (): boolean => {
  return getToken() !== null;
};

export const isAdmin = (): boolean => {
  const role = getRole();
  return role === 'ADMIN' || role === 'LEAD_ADMIN';
};

export const isLeadAdmin = (): boolean => {
  return getRole() === 'LEAD_ADMIN';
};
