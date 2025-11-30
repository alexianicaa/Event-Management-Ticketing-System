export const API_BASE = {
  auth: 'http://localhost:8081/api/auth',
  users: 'http://localhost:8081/api/users',
  events: 'http://localhost:8082/api/events',
  bookings: 'http://localhost:8083/api/bookings'
};

export const apiCall = async (url, options = {}) => {
  const token = localStorage.getItem('token');
  const headers = {
    'Content-Type': 'application/json',
    ...(token && { Authorization: `Bearer ${token}` }),
    ...options.headers
  };

  const response = await fetch(url, { ...options, headers });
  
  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Request failed' }));
    console.error('API Error Response:', error); 
    throw new Error(error.message || 'Request failed');
  }
  
  return response.json();
};