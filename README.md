# Event-Management-Ticketing-System
Platform for managing, discovering, and booking events with secure payments and analytics.

# Event Management & Ticketing System

# Team Members:
-Alexia-Stefania Nica
-Roman Gulida

# Project Description
The goal of this project is to develop a comprehensive Event Management & Ticketing System that allows organizers to create, manage, and analyze events while providing attendees with an easy way to discover and book tickets. he system provides functionality for both organizers and attendees, including event creation, ticket booking, secure payment, and analytics.

# Key features include:
-User Authentication & Roles: Secure login with distinct roles (attendee, organizer) and role-based access control.
-Event Creation & Management: Organizers can create, update, and delete events with details like type, date, and location.
-Ticket Booking & QR-Code Generation: Attendees can book tickets and receive QR codes for event entry.
-Secure Payment Processing: Multiple payment methods integrated with encryption for safe transactions.
-Analytics Dashboard: Organizers can track ticket sales, attendance, and user feedback to optimize future events.
-The project focuses on demonstrating object-oriented design principles and design pattern implementation, ensuring maintainable, extendable, and scalable code.

# Design Patterns
1. Singleton
Purpose: Ensures only one shared database connection exists, preventing multiple connections and ensuring consistent access to data.
Advantages: -Ensures only one database connection, avoiding conflicts and wasted resources.
             -Provides a global access point, simplifying database management.
2. Factory Method
Purpose: Creates different event types (concert, workshop, conference) without exposing the instantiation logic, making it easy to add new types.
Advantage: -Avoids hardcoding event types or using long if/else chains.
            -Makes adding new event types easy without changing existing code.
3. Observer
Purpose: Notifies attendees and organizers of ticket status changes (booked, canceled, refunded) in real-time.
Advantage: -Automatically notifies multiple components when ticket status changes.
            -Decouples the ticket from subscribers, making the system modular and easy to extend.
4. Strategy
Purpose: Supports multiple payment methods (credit card, PayPal, digital wallets) that can be swapped at runtime without modifying existing code.
Advantage: -Replaces complex conditional logic for payment selection.
            -Lets you swap or add payment methods without modifying existing code.
5. Decorator:
Purpose: Dynamically adds features to events, such as VIP access or merchandise bundles, without changing existing classes.
Advantage: -Adds or removes features dynamically without creating many subclasses.
            -Keeps base event class simple and easy to maintain.
