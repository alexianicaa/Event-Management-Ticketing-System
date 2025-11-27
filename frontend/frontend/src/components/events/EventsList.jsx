import { useState, useEffect } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { API_BASE, apiCall } from '../../api/api';

export default function EventsList({ onSelectEvent, onCreateNew }) {
  const { user } = useAuth();
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
  try {
    const data = await apiCall(`${API_BASE.events}`); 
    console.log('Loaded events:', data); 
    setEvents(data);
  } catch (err) {
    setError(err.message);
  } finally {
    setLoading(false);
  }
};

  if (loading) return <p>Loading events...</p>;
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;

  return (
    <div>
      <h2>Available Events</h2>
      {user?.role === 'ORGANIZER' && (
        <button onClick={onCreateNew}>Create New Event</button>
      )}
      <div>
        {events.length === 0 ? (
          <p>No events available</p>
        ) : (
          events.map((event) => (
            <div key={event.id} style={{ border: '1px solid black', padding: '10px', margin: '10px 0' }}>
              <h3>{event.title}</h3>
              <p><strong>Type:</strong> {event.eventType}</p>
              <p><strong>Date:</strong> {new Date(event.eventDateTime).toLocaleString()}</p>
              <p><strong>Location:</strong> {event.location}</p>
              <p><strong>Price:</strong> ${event.finalPrice || event.basePrice}</p>
              <p><strong>Available Tickets:</strong> {event.availableTickets}</p>
              {event.description && <p>{event.description}</p>}
              {event.artist && <p><strong>Artist:</strong> {event.artist}</p>}
              {event.genre && <p><strong>Genre:</strong> {event.genre}</p>}
              {event.instructor && <p><strong>Instructor:</strong> {event.instructor}</p>}
              {event.speakers && event.speakers.length > 0 && (
                <p><strong>Speakers:</strong> {event.speakers.join(', ')}</p>
              )}
              <button onClick={() => onSelectEvent(event)}>View Details / Book</button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}