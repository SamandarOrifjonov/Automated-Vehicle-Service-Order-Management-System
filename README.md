# Automated Vehicle Service Order Management System

## Programming Language

- **Java 21**

---

## Frameworks & Technologies

| Technology               | Purpose                          |
|--------------------------|----------------------------------|
| Spring Boot 3.5          | Core backend framework           |
| Spring Data JPA          | Database access (ORM)            |
| Spring Security Crypto   | BCrypt password hashing          |
| Hibernate                | JPA implementation               |
| Lombok                   | Boilerplate code reduction       |
| Maven                    | Dependency management and build  |

---

## Database

- **PostgreSQL 16**
- Tables are created automatically via JPA / Hibernate (`ddl-auto=update`)

---

## Getting Started

### 1. Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 16+

### 2. Set Up PostgreSQL

```bash
# Start PostgreSQL (macOS Homebrew)
brew services start postgresql@16

# Create the database
createdb vehicle_service_db
```

### 3. Configure `application.properties`

Edit `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vehicle_service_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 4. Run the Application

```bash
# Navigate to the project folder
cd vehicle-service

# Run with the Maven wrapper
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/login` | Login |

### Users
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/users/customers` | Register a customer |
| GET | `/api/users` | List all users (Admin) |

### Vehicles
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/vehicles?customerId={id}` | Add a vehicle |
| GET | `/api/vehicles/customer/{customerId}` | Get customer's vehicles |

### Service Types
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/service-types` | List all service types |
| POST | `/api/service-types` | Add a service type (Admin) |

### Orders
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/orders` | Create an order (Customer) |
| GET | `/api/orders` | List all orders (Manager) |
| GET | `/api/orders/customer/{customerId}` | Get customer's orders |
| GET | `/api/orders/mechanic/{mechanicId}` | Get mechanic's orders |
| PUT | `/api/orders/{id}/status?status=APPROVED` | Update order status |
| POST | `/api/orders/{id}/assign` | Assign a mechanic |
| POST | `/api/orders/parts` | Add a used part |
| POST | `/api/orders/{id}/invoice` | Generate an invoice |

---

## Roles

| Role | Permissions |
|------|-------------|
| `ADMIN` | Manage users and service types |
| `MANAGER` | View orders, approve/reject, assign mechanics, generate invoices |
| `MECHANIC` | View own orders, update progress, add used parts |
| `CUSTOMER` | Add vehicles, create orders, view own orders |

---

## Business Flow

```
Customer  → Create order            (PENDING)
Manager   → Approve / Reject        (APPROVED / CANCELLED)
Manager   → Assign mechanic
Mechanic  → Start work              (IN_PROGRESS)
Mechanic  → Add used parts          (totalPrice auto-updated)
Mechanic  → Complete work           (COMPLETED)
Manager   → Generate invoice
Customer  → View invoice and status
```
