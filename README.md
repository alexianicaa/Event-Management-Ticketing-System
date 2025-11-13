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
- Single server running frontend + backend  
- Single PostgreSQL database  
- Optional external payment gateway  

**Component Diagram:**
![](monolith_component.png)
- Monolith contains internal modules: User, Event, Ticketing, Payment, Analytics  
- All modules share the same database  
- Frontend communicates only with the monolith  

> *Diagrams can be created in StarUML or Mermaid and inserted here as images.*

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
