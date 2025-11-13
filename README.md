# Milestone 3: Software Architecture Analysis for Event Management & Ticketing System

## Team Members:
- Alexia-Stefania Nica
- Roman Gulida

## 1. Monolithic Architecture

### 1.1 Description

The Event Management & Ticketing System is implemented as a single, unified application, where all functionalities are tightly integrated within one codebase and deployed as a single unit. All modules share the same database, and inter-module communication happens internally via method calls.  

This architecture is suitable for a small team, where fast development and strong consistency in transactions (like ticket purchases and payments) are important.

---

### 1.2 Key Components

| Layer | Modules | Patterns|
|-------|---------|----------------|
| **Presentation Layer** | Web Controllers / Frontend | Handles HTTP requests from users (Attendees / Organizers) and renders responses or views |
| **Business Logic** | - User Management <br> - Event Management <br> - Ticketing <br> - Payment Processing <br> - Analytics & Reporting | Design patterns ensure modularity, flexibility, and maintainability within the monolith:<br>• User Management: Factory Pattern for Attendee/Organizer creation<br>• Event Management: Builder Pattern for complex event creation<br>• Ticketing: Strategy Pattern for ticket types / validation<br>• Payment Processing: Proxy Pattern for gateway integration |
| **Data Layer** | PostgreSQL database | Stores all system data: users, events, tickets, payments, analytics, and reviews |

---

### 1.3 Structure & Data Flow

**System Structure:**
- Single deployable unit: one backend application handles all functionalities.
- Modules inside the monolith:
  - **User Management**: Registration, authentication, roles (Organizer/Attendee)
  - **Event Management**: Create, update, delete, search events
  - **Ticketing**: Issue tickets, generate QR codes, validate tickets
  - **Payment Processing**: Handle payments with multiple gateways
  - **Analytics & Reporting**: Track sales, attendance, and user behavior
  - **Frontend Interface**: Web UI or API layer for clients
- **Database**: One shared relational database storing users, events, tickets, payments, and analytics

**Data Flow Example (Ticket Purchase):**
1. Attendee selects event → request sent to Event Controller  
2. Ticketing module generates ticket and validation info  
3. Payment module processes payment through Proxy → external gateway  
4. Database updated with ticket & payment information  
5. Confirmation response sent to Attendee  
6. Analytics module asynchronously logs sales and attendance  

---

### 1.4 Architecture Diagrams

**Deployment Diagram:**
![](monolith_deployment.png)


**Component Diagram:**
![](monolith_component.png)
 
---

### 1.5 Pros and Cons

**Advantages:**
- **Simple Development**: Single codebase, easy to understand and navigate  
- **Easy Testing**: End-to-end tests are straightforward  
- **Simple Deployment**: Only one app server to deploy and manage  
- **Strong Consistency**: ACID transactions across ticket purchases and payments  
- **Low Operational Overhead**: One backend + database  
- **Perfect for Small Team**: 3 developers can work efficiently  
- **Fast Time to Market**: Quickly build MVP to showcase core functionality  

**Disadvantages:**
- **Scaling Limitations**: Must scale entire application even if only Ticketing or Payment needs more resources  
- **Technology Lock-in**: Entire system must use same language/framework  
- **Single Point of Failure**: One bug can crash the whole system  
- **Deployment Risk**: Every update risks the entire application  
- **Long-term Maintenance**: As features grow (e.g., real-time notifications, recommendations), codebase can become unwieldy  

**Project-Specific Considerations:**
- Ideal for early launch or prototype of Event Management & Ticketing System  
- Can handle first 5,000–10,000 users without performance issues  
- Payment transactions benefit from ACID guarantees  
- Infrastructure costs remain low initially  

---

## 2. Microservices Architecture

### 2.1 Description

The Event Management & Ticketing System is decomposed into multiple independent services, each responsible for a specific domain functionality. Each service has its own database or schema, and services communicate through REST APIs (or gRPC) to coordinate operations.  

This architecture is suitable for scaling individual services independently, enabling multiple developers or teams to work in parallel, and supporting high availability and fault tolerance.

---

### 2.2 Key Components

| Microservice | Responsibilities | Patterns |
|--------------|-----------------|----------------|
| **User Service** | Handles user registration, authentication, roles (Attendee / Organizer) | Factory pattern for creating user types |
| **Event Service** | Create, update, delete, and search events | Builder pattern for complex event creation |
| **Ticketing Service** | Issue tickets, validate QR codes, manage ticket types | Strategy pattern for different ticketing rules |
| **Payment Service** | Handle payments through multiple gateways | Proxy pattern for external payment integration |
| **Analytics Service** | Tracks sales, attendance, and user behavior | Event sourcing for asynchronous analytics |
| **API Gateway** | Single entry point for frontend clients | Routes requests to appropriate microservices |
| **Frontend Interface** | Web UI or mobile client communicates with API Gateway | |

---

### 2.3 Structure & Data Flow

**System Structure:**
- Each microservice is an independent deployable unit (containerized or standalone)
- Microservices communicate via HTTP REST APIs (or optionally gRPC)
- Each service manages its own database (or schema) to ensure decoupling
- API Gateway handles routing, authentication, and aggregation for the frontend

**Data Flow Example (Ticket Purchase):**
1. Attendee selects event → request sent to **API Gateway**  
2. **Event Service** validates event availability  
3. **Ticketing Service** generates ticket and validation info  
4. **Payment Service** processes payment via Proxy → external gateway  
5. Each service updates its own database  
6. Confirmation response sent to Attendee via API Gateway  
7. **Analytics Service** asynchronously logs sale and attendance  

---

### 2.4 Architecture Diagrams

**Deployment Diagram:**
![](microservices_deployment.png)

**Component Diagram:**
![](microservices_components.png)


---

### 2.5 Pros and Cons

**Advantages:**
- **Independent Scaling**: Services can scale individually based on load  
- **Technology Flexibility**: Different services can use different languages or frameworks  
- **Fault Isolation**: Failure in one service does not crash the entire system  
- **Parallel Development**: Multiple teams can work on separate services  
- **Better Long-Term Maintenance**: Smaller codebases per service  

**Disadvantages:**
- **Increased Complexity**: More moving parts, requires orchestration and monitoring  
- **Inter-Service Communication Overhead**: Network latency and failure handling needed  
- **Deployment Complexity**: Multiple services require CI/CD and containerization  
- **Data Consistency**: Distributed databases require careful handling of consistency  

**Project-Specific Considerations:**
- Suitable for scaling Ticketing and Payment services independently during peak demand  
- Can support larger user base (10,000+ users) more efficiently than Monolith  
- Requires more infrastructure and DevOps effort for deployment  

