import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { API_BASE, apiCall } from '../../api/api';

export default function Login({ onSwitch }) {
  const { login } = useAuth();
  const [form, setForm] = useState({ usernameOrEmail: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await apiCall(`${API_BASE.auth}/login`, {
        method: 'POST',
        body: JSON.stringify(form)
      });
      login(response);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username or Email:</label>
          <input
            type="text"
            value={form.usernameOrEmail}
            onChange={(e) => setForm({ ...form, usernameOrEmail: e.target.value })}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            required
          />
        </div>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Loading...' : 'Login'}
        </button>
      </form>
      <p>
        Don't have an account? <button type="button" onClick={onSwitch}>Register</button>
      </p>
    </div>
  );
}