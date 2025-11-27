import { createContext, useState, useEffect } from 'react';

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token'));

  useEffect(() => {
    const setData = () => {
      if (token) {
        const userData = JSON.parse(localStorage.getItem('user') || 'null');
        setUser(userData);
      }
    }

    setData()
  }, [token]);

  const login = (authResponse) => {
    const userData = {
      userId: authResponse.userId,
      username: authResponse.username,
      email: authResponse.email,
      role: authResponse.role
    };
    
    setToken(authResponse.token);
    setUser(userData);
    localStorage.setItem('token', authResponse.token);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};