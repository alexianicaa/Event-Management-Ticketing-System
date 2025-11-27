import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { API_BASE, apiCall } from '../../api/api';

export default function CreateEvent({ onBack }) {
  const { user } = useAuth();
  const [form, setForm] = useState({
    eventType: 'CONCERT',
    title: '',
    description: '',
    eventDateTime: '',
    location: '',
    basePrice: '',
    availableTickets: '',
    organizerId: user?.userId,
    artist: '',
    genre: '',
    instructor: '',
    maxParticipants: '',
    speakers: '',
    tracks: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const payload = {
        ...form,
        basePrice: parseFloat(form.basePrice),
        availableTickets: parseInt(form.availableTickets),
        maxParticipants: form.maxParticipants ? parseInt(form.maxParticipants) : null,
        speakers: form.speakers ? form.speakers.split(',').map(s => s.trim()).filter(Boolean) : null,
        tracks: form.tracks ? form.tracks.split(',').map(t => t.trim()).filter(Boolean) : null
      };

      console.log('Sending payload:', payload); 

      await apiCall(`${API_BASE.events}`, {
        method: 'POST',
        body: JSON.stringify(payload)
      });

      setSuccess(true);
      setTimeout(() => onBack(), 2000);
    } catch (err) {
      console.error('Full error:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div>
        <h2>Event created successfully!</h2>
        <button onClick={onBack}>Back to Events</button>
      </div>
    );
  }

  return (
    <div>
      <h2>Create New Event</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Event Type:</label>
          <select value={form.eventType} onChange={(e) => setForm({ ...form, eventType: e.target.value })} required>
            <option value="CONCERT">Concert</option>
            <option value="WORKSHOP">Workshop</option>
            <option value="CONFERENCE">Conference</option>
          </select>
        </div>
        <div>
          <label>Title:</label>
          <input
            type="text"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
            required
            minLength={3}
            maxLength={200}
          />
        </div>
        <div>
          <label>Description:</label>
          <textarea
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
            maxLength={2000}
          />
        </div>
        <div>
          <label>Date & Time:</label>
          <input
            type="datetime-local"
            value={form.eventDateTime}
            onChange={(e) => setForm({ ...form, eventDateTime: e.target.value })}
            required
          />
        </div>
        <div>
          <label>Location:</label>
          <input
            type="text"
            value={form.location}
            onChange={(e) => setForm({ ...form, location: e.target.value })}
            required
          />
        </div>
        <div>
          <label>Base Price ($):</label>
          <input
            type="number"
            step="0.01"
            min="0.01"
            value={form.basePrice}
            onChange={(e) => setForm({ ...form, basePrice: e.target.value })}
            required
          />
        </div>
        <div>
          <label>Available Tickets:</label>
          <input
            type="number"
            min="1"
            value={form.availableTickets}
            onChange={(e) => setForm({ ...form, availableTickets: e.target.value })}
            required
          />
        </div>

        {form.eventType === 'CONCERT' && (
          <>
            <div>
              <label>Artist:</label>
              <input
                type="text"
                value={form.artist}
                onChange={(e) => setForm({ ...form, artist: e.target.value })}
              />
            </div>
            <div>
              <label>Genre:</label>
              <input
                type="text"
                value={form.genre}
                onChange={(e) => setForm({ ...form, genre: e.target.value })}
              />
            </div>
          </>
        )}

        {form.eventType === 'WORKSHOP' && (
          <>
            <div>
              <label>Instructor:</label>
              <input
                type="text"
                value={form.instructor}
                onChange={(e) => setForm({ ...form, instructor: e.target.value })}
              />
            </div>
            <div>
              <label>Max Participants:</label>
              <input
                type="number"
                min="1"
                value={form.maxParticipants}
                onChange={(e) => setForm({ ...form, maxParticipants: e.target.value })}
              />
            </div>
          </>
        )}

        {form.eventType === 'CONFERENCE' && (
          <>
            <div>
              <label>Speakers (comma-separated):</label>
              <input
                type="text"
                value={form.speakers}
                onChange={(e) => setForm({ ...form, speakers: e.target.value })}
                placeholder="John Doe, Jane Smith"
              />
            </div>
            <div>
              <label>Tracks (comma-separated):</label>
              <input
                type="text"
                value={form.tracks}
                onChange={(e) => setForm({ ...form, tracks: e.target.value })}
                placeholder="AI, Web Development, Cloud"
              />
            </div>
          </>
        )}

        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Creating...' : 'Create Event'}
        </button>
        <button type="button" onClick={onBack}>Cancel</button>
      </form>
    </div>
  );
}