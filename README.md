# Event Management System - Services Extension

[![CI/CD Status](https://github.com/alexianicaa/Event-Management-Ticketing-System/actions/workflows/ci-cd.yml/badge.svg?branch=5-services-extension)](https://github.com/alexianicaa/Event-Management-Ticketing-System/actions/workflows/ci-cd.yml)

A microservices project demonstrating **asynchronous communication with RabbitMQ** and **automated CI/CD deployment**.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [What's New in This Branch](#whats-new-in-this-branch)
- [Message Queue Integration (RabbitMQ)](#message-queue-integration-rabbitmq)
- [CI/CD Pipeline](#cicd-pipeline)
- [How to Run](#how-to-run)

---

## Project Overview

This is a **microservices-based event management system** where users can:
- Register and login (User Service)
- Create and browse events (Event Service)  
- Book tickets for events (Booking Service)

**Architecture:**

```
Frontend (3000) --→ User Service (8081)
                 --→ Event Service (8082)
                 --→ Booking Service (8083)
                            ↓
                        RabbitMQ (5672)
                            ↓
                     PostgreSQL DBs
```

---

## What's New in This Branch

This `5-services-extension` branch adds two major improvements:

### 1. **RabbitMQ Message Queue** 🐰
Instead of services calling each other directly (which can fail), they now communicate through messages.

**Why this matters:**
- If Event Service crashes, bookings still work
- Messages are saved until the service comes back online
- Services can run independently

### 2. **CI/CD Pipeline** 🚀
Automated pipeline that builds, tests, and deploys everything when you push code to GitHub.

**What it does:**
- Automatically builds all services
- Creates Docker images
- Deploys and tests the system
- Shows you if something broke

---

## Message Queue Integration (RabbitMQ)

### The Problem We're Solving

**Before (Direct HTTP Calls):**
```
User books ticket → Booking Service → HTTP call to Event Service
                                          ↓
                                   If Event Service is down?
                                   ❌ Booking fails!
```

**After (Message Queue):**
```
User books ticket → Booking Service → Message to RabbitMQ → Queue stores it
                                                                  ↓
                                                    Event Service picks it up
                                                    (whenever it's ready)
                                                    ✅ Always works!
```

### How It Works

#### Step-by-Step Flow

1. **User books a ticket** via frontend
2. **Booking Service** saves the ticket to database
3. **Booking Service** sends a message to RabbitMQ: 
   ```json
   {
     "ticketId": 1,
     "eventId": 2,
     "attendeeEmail": "user@example.com"
   }
   ```
4. **RabbitMQ** stores this message in a queue
5. **Event Service** reads the message from the queue
6. **Event Service** updates available tickets
7. **Done!** And if Event Service was offline, it processes messages when it comes back

#### Visual Diagram

```
┌─────────────────┐
│ Booking Service │  1. Creates ticket
└────────┬────────┘
         │
         │ 2. Publishes message
         ↓
┌─────────────────┐
│    RabbitMQ     │  3. Stores message
│  ticket.queue   │
└────────┬────────┘
         │
         │ 4. Delivers message
         ↓
┌─────────────────┐
│  Event Service  │  5. Updates tickets
└─────────────────┘
```

### Benefits We're Demonstrating

#### 1. Decoupling
**What it means:** Services don't know about each other

**Code comparison:**

**Before:**
```java
// Booking Service calling Event Service directly
eventServiceClient.updateTickets(eventId, 1);
// Problem: Need to know Event Service URL, it must be running
```

**After:**
```java
// Booking Service just publishes a message
eventPublisher.publishTicketBookedEvent(ticketEvent);
// Benefit: Don't care who consumes it or when
```

#### 2. Fault Tolerance
**What it means:** System keeps working even if parts fail

**Test this yourself:**
```bash
# Stop Event Service
docker stop event-service

# Book a ticket - it still works! ✅

# Check RabbitMQ UI - message is waiting in queue

# Start Event Service again
docker start event-service

# Watch logs - it processes the queued message! ✅
```

#### 3. Scalability
**What it means:** Can handle more load by adding instances

**Example:**
```
One Event Service = Processes 100 messages/sec
Add another instance = 200 messages/sec
Add third instance = 300 messages/sec
```

RabbitMQ automatically distributes messages between all instances!

#### 4. Asynchronous Processing
**What it means:** User doesn't wait for everything to finish

**Response times:**

| Action | Before | After |
|--------|--------|-------|
| Book ticket | 500ms (wait for everything) | 50ms (instant response) |
| User experience | Waiting... | Done! |

### Configuration Files

**Location of RabbitMQ settings:**

**1. docker-compose.yml** - Starting RabbitMQ
```yaml
rabbitmq:
  image: rabbitmq:3.13-management-alpine
  ports:
    - "5672:5672"   # For services to connect
    - "15672:15672" # For web dashboard
```

**2. application.yml** - Connecting to RabbitMQ
```yaml
spring:
  rabbitmq:
    host: rabbitmq  # Service name in Docker
    port: 5672
```

**3. Java Config** - Creating queues

**Booking Service** (`RabbitMQConfig.java`):
```java
// Creates exchange and queue
@Bean
public Queue ticketQueue() {
    return new Queue("ticket.queue", true);
}

// Publisher
eventPublisher.publishTicketBookedEvent(event);
```

**Event Service** (`TicketEventListener.java`):
```java
// Listens to queue
@RabbitListener(queues = "ticket.queue")
public void handleTicketBookedEvent(TicketBookedEvent event) {
    eventService.updateAvailableTickets(event.getEventId(), 1);
}
```

---

## CI/CD Pipeline

### What is CI/CD?

**CI (Continuous Integration):** Automatically build and test code when you push to GitHub  
**CD (Continuous Deployment):** Automatically deploy if tests pass

**Why it's useful:**
- Catches errors immediately
- No manual building
- Everyone sees if build is broken
- Professional development practice

### Our Pipeline

**Trigger:** Every push to `5-services-extension` branch

**What happens automatically:**

```
You push code to GitHub
        ↓
GitHub Actions starts
        ↓
┌─────────────────────────┐
│ Job 1: Build Services   │ ← Compile Java code
│ • User Service          │
│ • Event Service         │
│ • Booking Service       │
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Job 2: Build Docker     │ ← Create Docker images
│ • 3 service images      │
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Job 3: Deploy & Test    │ ← Start everything
│ • docker compose up     │
│ • Health checks         │
│ • Verify it works       │
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Job 4: Report Results   │ ← Show summary
│ ✅ Success or ❌ Failed│
└─────────────────────────┘
```

### How to See It Running

#### Method 1: GitHub Actions Tab 

1. Go to your repository on GitHub
2. Click **"Actions"** tab at the top
3. See all pipeline runs

**You'll see:**
- ✅ Green checkmark = Success
- ❌ Red X = Something broke
- 🟡 Yellow circle = Currently running

#### Method 2: Watch Real-Time

1. Click on a running workflow
2. Click on any job (e.g., "Build & Test Microservices")
3. Watch live logs as it builds!


#### Method 3: Get Notifications

- GitHub emails you if build fails
- Can set up Slack notifications 

### Pipeline Configuration

**File location:** `.github/workflows/ci-cd.yml`

**Key parts explained:**

**1. When to run:**
```yaml
on:
  push:
    branches:
      - 5-services-extension  # Only this branch
```

**2. What environment:**
```yaml
runs-on: ubuntu-latest  # Free Ubuntu VM from GitHub
```

**3. Build steps:**
```yaml
- name: Build User Service
  run: ./mvnw clean package -DskipTests
  # Compiles Java, creates JAR file
```

**4. Docker build:**
```yaml
- name: Build User Service Image
  uses: docker/build-push-action@v5
  # Creates Docker image (but doesn't push anywhere)
```

**5. Deployment:**
```yaml
- name: Deploy with Docker Compose
  run: docker compose up -d --build
  # Starts all services
```

**6. Tests:**
```yaml
- name: Health Check - User Service
  run: curl --fail http://localhost:8081/api/auth/health
  # Checks if service responds
```

---

## How to Run

### Prerequisites

Make sure you have:
- Docker Desktop installed
- Git installed
- Available ports: 5432-5434, 5672, 8081-8083, 15672

### Step 1: Clone & Checkout

```bash
# Clone the repository
git clone https://github.com/alexianicaa/Event-Management-Ticketing-System.git
cd Event-Management-Ticketing-System

# Switch to the services extension branch
git checkout 5-services-extension
cd event-management-system
```

### Step 2: Start Everything

```bash
# Start all services with Docker Compose
docker compose up -d

# Check if running
docker ps
```

You should see 7 containers running:
- user-service
- event-service
- booking-service
- rabbitmq
- postgres-user-db
- postgres-event-db
- postgres-booking-db

### Step 3: Access the System

| What | URL | Credentials |
|------|-----|-------------|
| Frontend | http://localhost:3000 | Register a new account |
| RabbitMQ Dashboard | http://localhost:15672 | guest / guest |
| User Service API | http://localhost:8081/api/auth/health | - |
| Event Service API | http://localhost:8082/api/events/health | - |
| Booking Service API | http://localhost:8083/api/bookings/health | - |

### Step 4: Stop Everything

```bash
# Stop all services
docker compose down

# Stop and remove all data 
docker compose down -v
```