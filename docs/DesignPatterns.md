# Design Patterns Documentation

This document explains the design patterns used in the Event Management & Ticketing System.

---

## 1) Singleton — `DatabaseConnection`

**Location:** `src/database/DatabaseConnection.java`

**Intent:** Ensure a single shared database connection across the system.

**Justification / Alternatives:**

* Guarantees only one instance; avoids multiple connections and race conditions.
* Alternative: Use a dependency injection (DI) container or connection pool to improve testability and scalability.
* Example: `DatabaseConnection.getInstance().getConnection()` ensures all services use the same connection.

---

## 2) Factory Method — `EventFactory`

**Location:** `src/factory/EventFactory.java`

**Intent:** Decouple event creation logic from client code.

**Justification / Alternatives:**

* Simplifies adding new event types (concert, workshop, conference) without modifying existing code.
* Alternative: Direct constructors or enum-based factory for small sets; Abstract Factory if multiple related objects must be created together.

**Example usage:**

```java
EventFactory factory = EventFactory.getFactory("concert");
Event e = factory.createAndConfigureEvent("Hip Hop", "Live concert", dateTime, "Stadium", 50.0, 500);
```

* The client code does not need to know the concrete subclass, demonstrating decoupling and extensibility.

---


## 3) Observer — Ticket Status Notifications

**Location:** `src/observer/TicketObserver.java`

**Intent:** Notify attendees/organizers and analytics services when ticket state changes (BOOKED, CANCELLED, REFUNDED).

**Justification / Alternatives:**

* Decouples subjects from observers; supports modular, extendable system.
* Alternative: Asynchronous message bus (e.g., Kafka) for higher throughput or eventual consistency.
* Observers registered via event bus; notifications can be synchronous or asynchronous depending on subscriber.

**Example usage:**

```java
TicketSubject subject = new TicketSubject();
TicketObserver emailObserver = new EmailNotificationObserver();
TicketObserver analyticsObserver = new AnalyticsObserver();

subject.attach(emailObserver);
subject.attach(analyticsObserver);
subject.notifyObservers(ticket, "TICKET_CONFIRMED");
```

* This demonstrates real-time notifications and loose coupling between the ticket system and observers.

---

## 4) Strategy — Payment Methods

**Location:** `src/strategy/PaymentStrategy.java`

**Intent:** Support multiple payment methods (credit card, PayPal, wallet) that can be swapped at runtime.

**Justification / Alternatives:**

* Encapsulates varying algorithms; PaymentContext delegates execution to the chosen strategy.
* Alternative: Template Method if common flow exists and variations are small.
* Each strategy handles its own validation and error handling.

**Example usage:**

```java
PaymentStrategy strategy = new CreditCardPayment("1234567812345678", "123", "12/25", "John Doe");
PaymentProcessor processor = new PaymentProcessor(strategy);
processor.executePayment(100.0);

processor.setPaymentStrategy(new PayPalPayment("user@example.com", "password"));
processor.executePayment(200.0);
```

* Demonstrates runtime swapping of strategies.

---

## 5) Decorator — Event Add-ons

**Location:** `src/decorator/EventDecorator.java`

**Intent:** Dynamically add features to events (VIP, merchandise bundles) without subclass explosion.

**Justification / Alternatives:**

* Maintains same interface as base Event; allows runtime composition.
* Alternative: Composite pattern if features are stateful or need grouping.

**Example usage:**

```java
Event basicEvent = new Concert();
basicEvent.setTitle("Rock Night");
basicEvent.setBasePrice(50.0);

Event vipEvent = new VIPAccessDecorator(basicEvent);
Event fullEvent = new MerchandiseBundleDecorator(vipEvent);

System.out.println("Total price: $" + fullEvent.getPrice());
System.out.println(fullEvent.getEventDetails());
```

* Demonstrates runtime composition of features and price calculation.


