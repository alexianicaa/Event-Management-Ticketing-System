# Event Management System - Docker Setup

A microservices-based event management system with three Spring Boot services and PostgreSQL databases, fully containerized with Docker.

---

## 📋 Prerequisites

Before running the system, ensure you have the following installed:

- **Docker** (version 20.10 or higher)
- **Docker Compose** (version 2.0 or higher)

To verify your installation:

```bash
docker --version
docker-compose --version
```

---

## 🏗️ System Architecture

The system consists of the following services:

| Service | Port | Database | Description |
|---------|------|----------|-------------|
| **User Service** | 8081 | postgres-user (5432) | Handles user authentication and management |
| **Event Service** | 8082 | postgres-event (5433) | Manages events and event details |
| **Booking Service** | 8083 | postgres-booking (5434) | Handles event bookings and reservations |


---

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd event-management-system
```

### 2. Build and Start All Services

Run the following command from the project root directory:

```bash
docker-compose up --build -d
```

**What this does:**
- `--build`: Builds Docker images for all services
- `-d`: Runs containers in detached mode (background)

**First-time setup** takes approximately 3-5 minutes as Docker builds all images and downloads dependencies.

### 3. Verify Services are Running

Check that all containers are up:

```bash
docker ps
```

You should see 6 running containers:
- `user-service`
- `event-service`
- `booking-service`
- `postgres-user-db`
- `postgres-event-db`
- `postgres-booking-db`

### 4. Health Check

Test each service to ensure they're responding:

```bash
# User Service
curl http://localhost:8081/api/auth/health

# Event Service
curl http://localhost:8082/api/events/health

# Booking Service
curl http://localhost:8083/api/bookings/health
```

Expected response: `200 OK` with a success message

---

## 🛠️ Common Commands

### Start Services

```bash
docker-compose up -d
```

### Stop Services

```bash
docker-compose down
```

### Stop and Remove All Data (Volumes)

```bash
docker-compose down -v
```

⚠️ **Warning:** This deletes all database data!

### Rebuild Services After Code Changes

```bash
docker-compose up --build -d
```

### View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker logs user-service -f
docker logs event-service -f
docker logs booking-service -f
```

Press `Ctrl+C` to exit log viewing.

### Restart a Single Service

```bash
docker-compose restart user-service
```

### Check Service Status

```bash
docker-compose ps
```

---

## 📊 Database Access

Each service has its own PostgreSQL database:

### User Service Database

```bash
docker exec -it postgres-user-db psql -U postgres -d user_db
```

### Event Service Database

```bash
docker exec -it postgres-event-db psql -U postgres -d event_db
```

### Booking Service Database

```bash
docker exec -it postgres-booking-db psql -U postgres -d booking_db
```

**Database Credentials:**
- Username: `postgres`
- Password: `postgres`

To exit PostgreSQL: type `\q` and press Enter

---

## 🧪 Testing the System

### Using Postman or cURL

#### 1. Register a New User

```bash
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "role": "ATTENDEE"
}
```

#### 2. Login

```bash
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "john_doe",
  "password": "password123"
}
```

Save the JWT token from the response for authenticated requests.

#### 3. Create an Event (Authenticated)

```bash
POST http://localhost:8082/api/events
Content-Type: application/json
Authorization: Bearer <YOUR_JWT_TOKEN>

{
  "name": "Tech Conference 2025",
  "description": "Annual technology conference",
  "location": "San Francisco",
  "startDate": "2025-06-15T09:00:00",
  "endDate": "2025-06-15T18:00:00",
  "capacity": 500,
  "category": "CONFERENCE"
}
```

#### 4. Create a Booking (Authenticated)

```bash
POST http://localhost:8083/api/bookings
Content-Type: application/json
Authorization: Bearer <YOUR_JWT_TOKEN>

{
  "eventId": 1,
  "numberOfTickets": 2
}
```

---

## 🔍 Troubleshooting

### Services Won't Start

1. **Check if ports are already in use:**
   ```bash
   # Windows
   netstat -ano | findstr "8081"
   netstat -ano | findstr "8082"
   netstat -ano | findstr "8083"
   ```

2. **Stop any conflicting services** and try again

### Database Connection Issues

1. **Ensure databases are running:**
   ```bash
   docker ps | grep postgres
   ```

2. **Check database logs:**
   ```bash
   docker logs postgres-user-db
   docker logs postgres-event-db
   docker logs postgres-booking-db
   ```

### Service Keeps Restarting

1. **Check service logs for errors:**
   ```bash
   docker logs user-service
   docker logs event-service
   docker logs booking-service
   ```

2. **Common issues:**
   - Database not ready (wait 30 seconds and check again)
   - Port conflicts
   - Maven build failures

### Complete System Reset

If everything fails, perform a complete reset:

```bash
# Stop and remove all containers, networks, and volumes
docker-compose down -v

# Remove all images
docker-compose rm -f

# Rebuild from scratch
docker-compose up --build -d
```

---

## 📝 Project Structure

```
event-management-system/
├── docker-compose.yml          # Main Docker Compose configuration
├── user-service/
│   ├── Dockerfile             # User service container definition
│   ├── pom.xml               # Maven dependencies
│   └── src/                  # Source code
├── event-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── booking-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
```

---

## 🎯 API Endpoints Summary

### User Service (Port 8081)
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `GET /api/auth/health` - Health check

### Event Service (Port 8082)
- `GET /api/events` - List all events
- `POST /api/events` - Create event (Auth required)
- `GET /api/events/{id}` - Get event details
- `PUT /api/events/{id}` - Update event (Auth required)
- `DELETE /api/events/{id}` - Delete event (Auth required)
- `GET /api/events/health` - Health check

### Booking Service (Port 8083)
- `GET /api/bookings` - List user bookings (Auth required)
- `POST /api/bookings` - Create booking (Auth required)
- `GET /api/bookings/{id}` - Get booking details (Auth required)
- `DELETE /api/bookings/{id}` - Cancel booking (Auth required)
- `GET /api/bookings/health` - Health check

---

## Frontend Setup (React + Vite)

A simple React frontend is included in the project to interact with all three backend services (User, Event, Booking). The frontend runs independently on http://localhost:3000
 and communicates with the backend services through REST API calls using the native fetch() API.

### 📁 Frontend Structure
```
frontend/
├── src/
│   ├── components/       # Reusable UI components
│   ├── pages/            # Pages (Login, Register, Events, Bookings)
│   ├── App.jsx           # Main application entry
│   └── main.jsx          # Vite entry point
├── package.json
├── vite.config.js
└── index.html
```

🚀 Running the Frontend
1. Install Dependencies

From the ```/frontend``` directory:
```
npm install
```
2. Start the Development Server
```
npm run dev
```

The frontend will run at:

👉 http://localhost:3000/

This server supports hot reload and automatically refreshes when you change code.

### 🔌 Connecting Frontend to Backend

All backend services run inside Docker:

| Service         | URL                                            |
| --------------- | ---------------------------------------------- |
| User Service    | [http://localhost:8081](http://localhost:8081) |
| Event Service   | [http://localhost:8082](http://localhost:8082) |
| Booking Service | [http://localhost:8083](http://localhost:8083) |


Since Vite runs locally (not inside Docker), no extra configuration is needed for cross-service communication.

### 🧹 Clean Install Instructions (Full System)

To run everything fresh:
```
docker-compose down -v
docker-compose up --build -d
cd frontend
npm install
npm run dev
```