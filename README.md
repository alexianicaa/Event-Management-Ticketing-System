# Milestone 3: Software Architecture Analysis for Event Management & Ticketing System

## Team Members:
- Alexia-Stefania Nica
- Roman Gulida

---

## 1. Monolithic Architecture

### 1.1 Description

The Event Management & Ticketing System is implemented as a single, unified application, where all functionalities are tightly integrated within one codebase and deployed as a single unit. All modules share the same database, and inter-module communication happens internally via method calls.  

This architecture is suitable for a small team, where fast development and strong consistency in transactions (like ticket purchases and payments) are important.

---

### 1.2 Key Components

| Layer | Modules | Patterns / Notes |
|-------|---------|----------------|
| **Presentation Layer** | Web Controllers / Frontend | Handles HTTP requests from users (Attendees / Organizers) and renders responses or views |
| **Business Logic** | - User Management <br> - Event Management <br> - Ticketing <br> - Payment Processing <br> - Analytics & Reporting | Design patterns used:<br>• User Management: Factory Method for creating Attendee/Organizer<br>• Event Management: Decorator for dynamically adding event features<br>• Ticketing: Observer to notify attendees/organizers about ticket status changes<br>• Payment Processing: Strategy for multiple payment methods<br>• Analytics & Reporting: Observer to asynchronously log events |
| **Data Layer** | PostgreSQL database | Stores all system data: users, events, tickets, payments, analytics, and reviews (Singleton pattern for shared DB connection) |

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
2. Ticketing module generates ticket and triggers **Observer** notifications  
3. Payment module processes payment using **Strategy** → external gateway  
4. Database updated via **Singleton** connection  
5. Confirmation response sent to Attendee  
6. Analytics module asynchronously logs sale and attendance using **Observer**  

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
- **Easy Testing**: End-to-end tests straightforward  
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
- **Ideal for early launch** or prototype of Event Management & Ticketing System  
- **Can handle first 5,000–10,000 users** without performance issues  
- **Payment transactions benefit from ACID guarantees**  
- **Infrastructure costs remain low initially**

---

## 2. Microservices Architecture

### 2.1 Description

The system is decomposed into multiple independent services, each responsible for a specific domain. Services communicate through REST APIs and each manages its own database (or schema).  

This architecture enables independent scaling, parallel development, and high availability.

---

### 2.2 Key Components

| Microservice | Responsibilities | Patterns |
|--------------|-----------------|----------------|
| **User Service** | User registration, authentication, roles | Factory Method |
| **Event Service** | Event creation, update, delete, search | Decorator |
| **Ticketing Service** | Generate tickets, validate QR codes | Observer |
| **Payment Service** | Handle multiple payment methods | Strategy |
| **Analytics Service** | Track sales, attendance | Observer |
| **API Gateway** | Routes requests from frontend to services | - |
| **Frontend Interface** | Web UI / Mobile Client | - |

---

### 2.3 Structure & Data Flow

**System Structure:**
- Each service is an independent deployable unit  
- API Gateway handles routing, authentication, and aggregation  
- Services communicate via REST APIs  
- Each service manages its own database using **Singleton** pattern for DB connection  

**Data Flow Example (Ticket Purchase):**
1. Attendee selects event → request sent to **API Gateway**  
2. **Event Service** validates availability  
3. **Ticketing Service** generates ticket → triggers **Observer**  
4. **Payment Service** processes payment (**Strategy**)  
5. Databases updated independently (**Singleton**)  
6. **Analytics Service** asynchronously logs sale (**Observer**)  
7. Confirmation sent to Attendee via API Gateway  

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

---

## 3. Event-Driven Architecture

### 3.1 Description

The system is structured around events and an event broker. Services communicate asynchronously by publishing and subscribing to events.  

This architecture improves decoupling, scalability, and responsiveness.

---

### 3.2 Key Components

| Component | Responsibilities | Patterns |
|-----------|-----------------|----------------|
| **Event Broker** | Handles events published by services and delivers to subscribers | - |
| **User Service** | Manages users and publishes events on creation/update | Factory Method |
| **Event Service** | Manages events, publishes notifications | Decorator |
| **Ticketing Service** | Issues tickets and publishes status changes | Observer |
| **Payment Service** | Processes payments and emits events | Strategy |
| **Analytics Service** | Subscribes to events to log and analyze | Observer |
| **Frontend Interface** | Web UI or mobile client publishes user actions | - |

---

### 3.3 Structure & Data Flow

**System Structure:**
- Services communicate asynchronously via **Event Broker**  
- Each service manages its own database (**Singleton**)  
- Frontend publishes actions, subscribes to updates via broker  

**Data Flow Example (Ticket Purchase):**
1. Attendee selects event → frontend publishes **TicketRequested** event  
2. **Ticketing Service** generates ticket → publishes **TicketCreated** event (**Observer**)  
3. **Payment Service** processes payment (**Strategy**) → publishes **PaymentCompleted**  
4. **Analytics Service** subscribes to all events to update logs (**Observer**)  
5. Attendee receives confirmation asynchronously  

---

### 3.4 Architecture Diagrams

**Deployment Diagram:**  
![](eventdriven_deployment.png)

**Component Diagram:**  
![](eventdriven_components.png)

---

### 3.5 Pros and Cons

**Advantages:**
- **High Scalability**: Services can scale independently without affecting others  
- **Loose Coupling**: Modules are decoupled, making maintenance easier  
- **Asynchronous Processing**: System can handle high load efficiently; frontend is not blocked by long operations  
- **Extensibility**: Adding new services (e.g., recommendations, chat) only requires subscribing to events  
- **Fault Isolation**: Failure in one service does not break the whole system  

**Disadvantages:**
- **Complexity**: Requires careful orchestration, monitoring, and logging  
- **Eventual Consistency**: Data may not be immediately consistent across services  
- **Debugging Difficulty**: Harder to trace events through the system  
- **Infrastructure Costs**: Event Broker, message queues, and multiple services increase deployment overhead  

**Project-Specific Considerations:**
- **Excellent for scaling Ticketing and Payment services** during high-demand events  
- **Allows real-time notifications and analytics dashboards**  
- **Requires more DevOps effort** than Monolithic architecture

---

## 4. Architecture Comparison

| Feature | Monolithic | Microservices | Event-Driven |
|---------|------------|---------------|--------------|
| Complexity | Low | Medium | High |
| Scalability | Low | High | High |
| Deployment | Simple | Medium | Medium |
| Fault Isolation | Low | High | High |
| Development Speed | High | Medium | Medium |
| Data Consistency | Strong | Distributed | Eventual |
| Team Suitability | Small | Medium/Large | Medium/Large |

**Recommended Architecture:**  
For the Event Management & Ticketing System MVP, **Monolithic Architecture** is most suitable due to its simplicity, strong transactional consistency, low infrastructure cost, and suitability for a small development team. Microservices or Event-Driven architectures are better for scaling later when user demand increases.

