import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { API_BASE, apiCall } from '../../api/api';

export default function BookEvent({ event, onBack }) {
  const { user } = useAuth();
  const [form, setForm] = useState({
    paymentMethod: 'CREDIT_CARD',
    cardNumber: '',
    cvv: '',
    expiryDate: '',
    cardHolderName: '',
    email: '',
    withVip: false,
    withMerchandise: false
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
        eventId: event.id,
        attendeeId: user.userId,
        withVip: form.withVip,
        withMerchandise: form.withMerchandise,
        payment: {
          paymentMethod: form.paymentMethod,
          ...(form.paymentMethod === 'CREDIT_CARD' && {
            cardNumber: form.cardNumber,
            cvv: form.cvv,
            expiryDate: form.expiryDate,
            cardHolderName: form.cardHolderName
          }),
          ...(form.paymentMethod === 'PAYPAL' && {
            email: form.email,
            token: 'mock-token-' + Date.now()
          })
        }
      };

      await apiCall(`${API_BASE.bookings}`, {
        method: 'POST',
        body: JSON.stringify(payload)
      });

      setSuccess(true);
      setTimeout(() => onBack(), 2000);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (user?.role !== 'ATTENDEE') {
    return (
      <div>
        <h2>Access Denied</h2>
        <p>Only attendees can book tickets</p>
        <button onClick={onBack}>Back</button>
      </div>
    );
  }

  if (success) {
    return (
      <div>
        <h2>Booking Successful!</h2>
        <p>Your ticket has been booked successfully</p>
        <button onClick={onBack}>Back to Events</button>
      </div>
    );
  }

  const calculateTotal = () => {
    let total = event.finalPrice || event.basePrice;
    if (form.withVip) total += 50;
    if (form.withMerchandise) total += 25;
    return total.toFixed(2);
  };

  return (
    <div>
      <h2>Book Ticket for {event.title}</h2>
      <p><strong>Base Price:</strong> ${event.finalPrice || event.basePrice}</p>
      <p><strong>Date:</strong> {new Date(event.eventDateTime).toLocaleString()}</p>
      <p><strong>Location:</strong> {event.location}</p>

      <form onSubmit={handleSubmit}>
        <h3>Add-ons</h3>
        <div>
          <label>
            <input
              type="checkbox"
              checked={form.withVip}
              onChange={(e) => setForm({ ...form, withVip: e.target.checked })}
            />
            VIP Access (+$50)
          </label>
        </div>
        <div>
          <label>
            <input
              type="checkbox"
              checked={form.withMerchandise}
              onChange={(e) => setForm({ ...form, withMerchandise: e.target.checked })}
            />
            Merchandise (+$25)
          </label>
        </div>

        <p><strong>Total: ${calculateTotal()}</strong></p>

        <h3>Payment Details</h3>
        <div>
          <label>Payment Method:</label>
          <select value={form.paymentMethod} onChange={(e) => setForm({ ...form, paymentMethod: e.target.value })} required>
            <option value="CREDIT_CARD">Credit Card</option>
            <option value="PAYPAL">PayPal</option>
          </select>
        </div>

        {form.paymentMethod === 'CREDIT_CARD' && (
          <>
            <div>
              <label>Card Number:</label>
              <input
                type="text"
                value={form.cardNumber}
                onChange={(e) => setForm({ ...form, cardNumber: e.target.value })}
                placeholder="1234 5678 9012 3456"
                required
              />
            </div>
            <div>
              <label>CVV:</label>
              <input
                type="text"
                value={form.cvv}
                onChange={(e) => setForm({ ...form, cvv: e.target.value })}
                placeholder="123"
                maxLength={3}
                required
              />
            </div>
            <div>
              <label>Expiry Date (MM/YY):</label>
              <input
                type="text"
                value={form.expiryDate}
                onChange={(e) => setForm({ ...form, expiryDate: e.target.value })}
                placeholder="12/25"
                required
              />
            </div>
            <div>
              <label>Cardholder Name:</label>
              <input
                type="text"
                value={form.cardHolderName}
                onChange={(e) => setForm({ ...form, cardHolderName: e.target.value })}
                required
              />
            </div>
          </>
        )}

        {form.paymentMethod === 'PAYPAL' && (
          <div>
            <label>PayPal Email:</label>
            <input
              type="email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
              placeholder="your@email.com"
              required
            />
          </div>
        )}

        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Processing...' : 'Confirm Booking'}
        </button>
        <button type="button" onClick={onBack}>Cancel</button>
      </form>
    </div>
  );
}