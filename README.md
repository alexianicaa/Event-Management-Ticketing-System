# Event Management & Ticketing System
Platform for managing, discovering, and booking events with secure payments and analytics.

## Team Members:
- Alexia-Stefania Nica
- Roman Gulida

# Navigation
- [Project Description](#project-description)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
- [Error Handling](#error-handling)

# Project Description
The goal is to build an extensible Event Management & Ticketing System that allows organizers to create and maintain events, while attendees can easily discover and book them. The focus is on demonstrating object-oriented architecture and proper usage of design patterns to ensure maintainability, scalability and clarity of design.

# Key features
* **Role-based access** — Distinct dashboards and permissions for organizer vs attendee.
* **Event lifecycle** — Create, update, delete, filter by category/date/location.
* **Ticketing with QR** — Book tickets with generated QR-codes for entry validation.
* **Payment processing** — Modular strategies with encrypted handling.
* **Analytics** — Organizer can track sales, attendance, and feedback.

# Getting Started
```bash
git clone https://github.com/alexianicaa/Event-Management-Ticketing-System.git
cd event-management-system
...
```

# Error Handling
The system handles failures through layered validation and fail-safe execution:
* **Validation errors** — Invalid inputs (dates, price, roles) are rejected before persistence with descriptive messages.
* **Payment failures** — Payment strategies return a structured failure result; bookings are not stored until payment succeeds.
* **Database failures** — Database access is wrapped and throws custom exceptions with safe fallbacks when possible.
* **Uncaught exceptions** — Caught at the top service layer and wrapped into user-friendly responses.
