import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Adjust if your backend URL is different

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Register a new user
export const registerUser = async (userData) => {
  try {
    const response = await api.post('/api/auth/register', userData);
    if (response.data.jwt) {
      localStorage.setItem('token', response.data.jwt);
    }
    return response;
  } catch (error) {
    throw (error.response?.data || error.message || 'Unknown error');
  }
};

// Login user and store JWT token
export const loginUser = async (loginData) => {
  try {
    const response = await api.post('/api/auth/login', loginData);
    if (response.data.jwt) {
      localStorage.setItem('token', response.data.jwt);
    }
    return response;
  } catch (error) {
    throw (error.response?.data || error.message || 'Unknown error');
  }
};

// Fetch user profile (requires JWT token)
export const getProfile = async () => {
  const token = localStorage.getItem('token');
  if (!token) {
    throw new Error('No token found');
  }
  try {
    const response = await api.get('/api/auth/profile', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    throw (error.response?.data || error.message || 'Unknown error');
  }
};

// Logout (clear token and user info)
export const logoutUser = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  localStorage.removeItem('role');
};

// Add token to all requests globally
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
