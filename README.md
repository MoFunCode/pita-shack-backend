# Pita Shack Grill - Restaurant Backend API

> **Building something real for someone real.** A friend runs Pita Shack Grill and needed a way to manage their menu and handle online orders. This is that system.

## What This Is

A production-ready Spring Boot REST API that powers menu management, online ordering, and automated customer notifications for a local restaurant. Built iteratively with real business requirements, not just another CRUD tutorial.

## Tech Stack

**Backend Framework:** Spring Boot 3.5.7 (Java 17)
**Data Layer:** Spring Data JPA + Hibernate
**Database:** H2 (dev) → MySQL (production)
**Email:** JavaMail with Gmail SMTP
**Build Tool:** Maven
**Development Partner:** Claude CLI (seriously, it's like pair programming)

## Features

### Menu Management System
- Full CRUD operations for menu items
- Category-based filtering (Pitas, Sides, Drinks, etc.)
- Item availability toggling
- Image URL support for product photos
- Automatic timestamp tracking

### Order Processing
- Complete order workflow: Pending → In Progress → Completed/Cancelled
- Unique order number generation
- Support for both pickup and delivery orders
- Itemized order tracking with:
  - Price snapshots (captures menu prices at order time)
  - Quantity management
  - Customization notes per item
  - Automatic subtotal calculations
- Tax and delivery fee handling

### Automated Notifications
- Customer email confirmations on order placement
- Status update notifications (order ready, completed)
- Formatted order summaries with itemized details
- Professional email templates with business branding

## API Endpoints

### Menu Items (`/api/menu`)
```
GET    /api/menu                    # Get all menu items
GET    /api/menu/{id}              # Get specific item
GET    /api/menu/category/{name}   # Filter by category
POST   /api/menu                    # Create new item
PUT    /api/menu/{id}              # Update item
DELETE /api/menu/{id}              # Remove item
```

### Orders (`/api/orders`)
```
POST   /api/orders                       # Place new order
GET    /api/orders                       # Get all orders
GET    /api/orders/{id}                  # Get specific order
GET    /api/orders/number/{orderNumber}  # Track by order number
GET    /api/orders/status/{status}       # Filter by status
PUT    /api/orders/{id}/status           # Update order status
```

## Database Schema

**Three-table relational model:**

**menu_items** - Product catalog with pricing, descriptions, categories
**orders** - Customer info, order type, pricing breakdown, status tracking
**order_items** - Junction table linking orders to menu items with quantities and customizations

**Key Design Decision:** Order items store historical price/name data. This means if "Chicken Shawarma" goes from $12.99 to $14.99, past orders still show the original price. Important for accounting and customer trust.

## Development Approach

### Progressive Feature Development
This wasn't built in one sitting. Git history shows the evolution:
1. Started with menu CRUD operations
2. Added order system with proper relationships
3. Implemented email notification system
4. Currently on `feature/email-notifications` branch

### Current Branch Strategy
```
main                        # Stable production-ready code
└── feature/email-notifications  # ← Current work
```

### Working with Claude CLI
Here's something different about this project - I use Claude from the terminal as a development partner. Not just for "help me debug this," but actual collaborative development:

- **Architecture discussions:** "Should I store historical prices in order items?"
- **Code reviews:** Catches edge cases I miss (like null checks, validation)
- **Rapid iteration:** Implements boilerplate while I focus on business logic
- **Documentation:** Helps explain complex parts (like JPA relationships)

It's not about replacing programming knowledge - I write the logic, make the decisions, and own the architecture. But having an AI teammate that can instantly scaffold a service class or suggest better validation patterns? That's just efficient development.

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Gmail account (for email notifications)

### Setup

1. **Clone and navigate:**
   ```bash
   git clone <repository-url>
   cd restaurant-api
   ```

2. **Configure email (optional):**
   ```bash
   cp .env.example .env
   # Edit .env with your Gmail credentials
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the H2 console** (database viewer):
   ```
   http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:restaurantdb
   Username: sa
   Password: (leave blank)
   ```

The API runs at `http://localhost:8080`

### Testing the API

**Add a menu item:**
```bash
curl -X POST http://localhost:8080/api/menu \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Chicken Shawarma Pita",
    "description": "Marinated chicken, lettuce, tomatoes, pickles, garlic sauce",
    "price": 12.99,
    "category": "Pitas",
    "isAvailable": true
  }'
```

**Place an order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerPhone": "555-1234",
    "customerEmail": "john@example.com",
    "orderType": "pickup",
    "items": [
      {
        "menuItemId": 1,
        "quantity": 2,
        "customizations": "Extra garlic sauce"
      }
    ]
  }'
```

## Production Considerations

### Database Migration
Currently using H2 in-memory database for development (`create-drop` mode). For production:

1. Switch to MySQL (config already in `application.properties`)
2. Change `ddl-auto` from `create-drop` to `update`
3. Set up proper database backups
4. Consider adding Flyway/Liquibase for migration management

### Environment Variables
All sensitive config (email credentials, database passwords) uses environment variables. Never committed to Git.

### What's Next
- [ ] Add authentication/authorization (Spring Security + JWT)
- [ ] Implement order analytics dashboard
- [ ] Add payment integration (Stripe/Square)
- [ ] Real-time order status updates (WebSockets)
- [ ] Admin panel for restaurant staff

## Project Demo

Want to see this in action? Here's a quick walkthrough I sent to my friend showing the latest features:

**[Video Demo Link - https://youtu.be/OUMuEKMD4NQ]**

## Why This Project Matters

This isn't a portfolio piece for the sake of having a Spring Boot project. It's solving a real problem for a real business. That means:

- **Real constraints:** Budget, timeline, actual user needs
- **Real decisions:** Why H2 for dev? Why snapshot prices in order items?
- **Real iteration:** Features built based on feedback, not assumptions

If you're a recruiter reading this, here's what this project demonstrates:
- Full-stack backend development (API design, database modeling, email integration)
- Modern Spring Boot ecosystem (JPA, validation, dependency injection)
- RESTful API best practices
- Relational database design with proper relationships
- Production-readiness thinking (env vars, migration strategy)
- Git workflow with feature branches
- Practical AI tool usage in professional development

## Questions?

Feel free to reach out. I'm happy to discuss architectural decisions, trade-offs, or walk through any part of the codebase.

---

**Built with:** ☕ Java, 🍃 Spring Boot, and 🤖 Claude CLI
**License:** MIT
**Status:** Active Development
