import { useState, useEffect } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { API_BASE, apiCall } from '../../api/api';

export default function MyBookings({ onBack }) {
  const { user } = useAuth();
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadBookings();
  },);

  const loadBookings = async () => {
    try {
      const data = await apiCall(`${API_BASE.bookings}/attendee/${user.userId}`);
      setBookings(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (ticketId) => {
    if (!window.confirm('Are you sure you want to cancel this booking?')) return;

    try {
      await apiCall(`${API_BASE.bookings}/${ticketId}/cancel`, { method: 'PUT' });
      alert('Booking cancelled successfully');
      loadBookings();
    } catch (err) {
      alert('Failed to cancel: ' + err.message);
    }
  };

  if (loading) return <p>Loading bookings...</p>;
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;

  return (
    <div>
      <h2>My Bookings</h2>
      <button onClick={onBack}>Back to Events</button>
      <div>
        {bookings.length === 0 ? (
          <p>No bookings yet. Book your first event!</p>
        ) : (
          bookings.map((booking) => (
            <div key={booking.ticketId} style={{ border: '1px solid black', padding: '10px', margin: '10px 0' }}>
              <p><strong>Ticket ID:</strong> {booking.ticketId}</p>
              <p><strong>Event ID:</strong> {booking.eventId}</p>
              <p><strong>Price:</strong> ${booking.price}</p>
              <p><strong>Status:</strong> {booking.status}</p>
              <p><strong>Payment Method:</strong> {booking.paymentMethod}</p>
              <p><strong>Booking Date:</strong> {new Date(booking.bookingDate).toLocaleString()}</p>
              {booking.qrCode && <p><strong>QR Code:</strong> {booking.qrCode}</p>}
              {booking.status === 'CONFIRMED' && (
                <button onClick={() => handleCancel(booking.ticketId)}>Cancel Booking</button>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}