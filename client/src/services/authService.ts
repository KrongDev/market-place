import axios, { AxiosResponse } from 'axios';

const API_URL = '/api/v1/auth';

interface LoginResponse {
    success: boolean;
    data: {
        accessToken: string;
        userId: number;
        email: string;
        username: string;
        role: string;
    };
}

interface SignupData {
    email: string;
    password: string;
    username: string;
}

interface User {
    userId: number;
    email: string;
    username: string;
    role: string;
    accessToken?: string;
}

export const authService = {
    login: async (email: string, password: string): Promise<LoginResponse> => {
        const response: AxiosResponse<LoginResponse> = await axios.post(`${API_URL}/login`, { email, password });
        if (response.data.success) {
            localStorage.setItem('token', response.data.data.accessToken);
            localStorage.setItem('user', JSON.stringify(response.data.data));
        }
        return response.data;
    },

    signup: async (userData: SignupData): Promise<any> => {
        const response = await axios.post(`${API_URL}/signup`, userData);
        return response.data;
    },

    logout: (): void => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    getCurrentUser: (): User | null => {
        if (typeof window === 'undefined') return null;
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    },

    getToken: (): string | null => {
        if (typeof window === 'undefined') return null;
        return localStorage.getItem('token');
    }
};

// Add interceptor to include token in requests
axios.interceptors.request.use(config => {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    if (token && !config.url?.includes('/auth/')) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
