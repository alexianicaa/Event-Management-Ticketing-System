import { useState } from 'react';
import { useAuth } from './hooks/useAuth';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import EventsList from './components/events/EventsList';
import CreateEvent from './components/events/CreateEvent';
import BookEvent from './components/events/BookEvent';
import MyBookings from './components/bookings/MyBookings';

function App() {
  const { user, logout } = useAuth();
  const [view, setView] = useState('events');
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [authMode, setAuthMode] = useState('login');
  const [refreshKey, setRefreshKey] = useState(0); // ADD THIS

  if (!user) {
    return (
      <div style={{ padding: '20px' }}>
        {authMode === 'login' ? (
          <Login onSwitch={() => setAuthMode('register')} />
        ) : (
          <Register onSwitch={() => setAuthMode('login')} />
        )}
      </div>
    );
  }

  // ADD THIS FUNCTION
  const handleBackToEvents = () => {
    setView('events');
    setSelectedEvent(null);
    setRefreshKey(prev => prev + 1); // Force refresh
  };

  return (
    <div style={{ padding: '20px' }}>
      <header style={{ borderBottom: '2px solid black', paddingBottom: '10px', marginBottom: '20px' }}>
        <h1>Event Management System</h1>
        <p>Welcome, <strong>{user.username}</strong> ({user.role})</p>
        <nav>
          <button onClick={handleBackToEvents}>Events</button>
          {' '}
          {user.role === 'ATTENDEE' && (
            <>
              <button onClick={() => setView('mybookings')}>My Bookings</button>
              {' '}
            </>
          )}
          <button onClick={logout}>Logout</button>
        </nav>
      </header>

      <main>
        {view === 'events' && !selectedEvent && (
          <EventsList
            key={refreshKey} // ADD THIS - Forces component to remount
            onSelectEvent={(event) => setSelectedEvent(event)}
            onCreateNew={() => setView('create')}
          />
        )}

        {view === 'events' && selectedEvent && (
          <BookEvent
            event={selectedEvent}
            onBack={() => setSelectedEvent(null)}
          />
        )}

        {view === 'create' && (
          <CreateEvent onBack={handleBackToEvents} /> 
        )}

        {view === 'mybookings' && (
          <MyBookings onBack={handleBackToEvents} /> 
        )}
      </main>
    </div>
  );
}

export default App;