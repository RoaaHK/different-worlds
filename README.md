# DifferentWorlds

A bookstore REST API built with Spring Boot and Spring Security. Authors submit books, customers add them to a cart and check out, and administrators manage the platform.

The project covers the complete flow, from user registration and authentication to checkout, with a focus on JWT-based security, role-based access control, ownership checks, transactional business logic, and consistent error handling.

**Built with:** Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA (Hibernate), Lombok, Maven, and an in-memory H2 database.

## Key Features

- **Three user types.** Separate signup and login for customers, authors and administrators.
- **JWT authentication.** Stateless, with a custom `OncePerRequestFilter` that validates the token on each request. Logging out blacklists the token, so it stops working immediately.
- **Role-based access control.** Each role can only reach its own endpoints.
- **Ownership checks.** Users can only access their own data. For example, customer 2 cannot read customer 1's cart, even with a valid token.
- **Server-side checkout.** The client only states who is paying and by which method. The server takes the price from the book, validates stock, saves the purchase and payment, and clears the cart. The whole operation runs in a single `@Transactional` method, so if any step fails, nothing is saved.
- **Consistent error responses.** Errors always use the same JSON shape with the appropriate status code (400, 401, 403, 404, 409). For example, signing up with an existing username returns a `409` with a clear message.
- **Password rules.** A custom validator enforces 8 to 64 characters with at least one letter and one digit. Passwords are stored with BCrypt.
- **Controlled admin creation.** Nobody can sign up as an admin. The first admin is created at application startup, and only an admin can create more.

## Architecture

The code follows a layered structure: **Controller → Service → Repository → Entity**. Controllers only accept and return DTOs (entities are never exposed), and business rules live in the service layer.

```text
com.example.DifferentWorlds
├── Configuration            # Security, CORS, cache, password encoder
├── Controller               # REST endpoints
├── dto                      # Request and response objects
├── Entity                   # JPA entities (UserBaseEntity is the shared superclass)
├── Enums                    # Roles, categories, genres, and other enums
├── GlobalExceptionHandler   # Centralized error mapping
├── JWT                      # Token utilities and authentication filter
├── Repository               # Spring Data JPA repositories
├── Service                  # Business logic and transactions
├── UserDetails              # Custom user details and service
└── Validator                # Custom password constraint
```

Customers, authors and administrators share common fields through `UserBaseEntity`, a mapped superclass.

## Getting Started

### Prerequisites

- JDK 17 or newer
- Maven

### Run

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080`. The database lives in memory, so all data is wiped when the application stops.

On first start, the application creates an initial admin account using the values in `application.properties`. To choose your own admin password, set the `ADMIN_PASSWORD` environment variable before starting the application. The default values are for local development only.

## Testing with Postman

The `postman` folder contains a collection of about 75 requests. Import it, open the Collection Runner, and run the whole collection. Every request includes an automated check, so each one reports pass or fail.

The collection covers:

- Setup (creating users and logging in)
- Error responses and status codes
- Which role can access which endpoints
- A complete checkout flow
- Attempts to read other users' data
- Password changes
- Logout and token invalidation

The collection can be run repeatedly.

## API Endpoints

Send the token in the request header: `Authorization: Bearer <token>`.

### Authentication: `/api/auth`

| Method | Path | Access |
|---|---|---|
| POST | `/signup/customer`, `/signup/author` | Public |
| POST | `/signup/admin` | Admin |
| POST | `/login/customer`, `/login/author`, `/login/admin` | Public |
| POST | `/logout` | Authenticated |

### Store: `/api/store`

| Method | Path | Access |
|---|---|---|
| GET | `/`, `/{id}` | Authenticated |
| POST | `/` | Admin |
| DELETE | `/{id}` | Admin |
| PATCH | `/{id}/stock/increase`, `/{id}/stock/decrease`, `/{id}/discount` | Admin |

### Authors: `/api/authors` (the author themselves, or an admin)

| Method | Path |
|---|---|
| GET | `/{id}`, `/by-username/{userName}` |
| PATCH | `/{id}/password` |
| DELETE | `/{id}` |
| POST | `/works` |
| GET | `/works/{workId}` |

### Customers: `/api/customers` (the customer themselves, or an admin; listing all customers is admin only)

| Method | Path |
|---|---|
| GET | `/` (admin only), `/{id}`, `/by-username/{userName}` |
| PATCH | `/{id}/password`, `/{id}/email` |
| DELETE | `/{id}` |

### Cart: `/api/cart` (the customer only)

| Method | Path |
|---|---|
| GET | `/{customerId}` |
| POST | `/{customerId}/items` |
| PATCH | `/{customerId}/items/{itemId}` |
| DELETE | `/{customerId}/items/{itemId}`, `/{customerId}` |

### Purchases: `/api/purchases`

| Method | Path | Access |
|---|---|---|
| POST | `/` (checkout) | The customer |
| GET | `/{customerId}` (history) | The customer, or an admin |

### Payments and Admin (admin only)

| Method | Path |
|---|---|
| GET | `/api/payments`, `/api/payments/{id}`, `/api/payments/between` |
| GET | `/api/admin/{id}`, `/api/admin/by-username/{userName}` |
| PATCH | `/api/admin/{id}/password` |
| PATCH | `/api/admin/works/{workId}/approve`, `/reject` |
| DELETE | `/api/admin/{id}` |

## Known Limitations & Future Improvements

- **Work approval is incomplete.** The approve and reject endpoints exist, but no approval status is applied behind them, so every submitted work currently appears in the store.
- **In-memory state.** The token blacklist and the database are both in memory, so a restart clears them. Running multiple instances would require a shared blacklist such as Redis.
- **Stock concurrency.** If two customers buy the last copy at the same moment, it could be oversold. Fixing this requires database-level locking (optimistic with `@Version`, or pessimistic).
- **H2 console exposed.** The console at `/h2-console` is enabled for development convenience. It must be disabled before any real deployment.

## Production Considerations

The passwords and keys in `application.properties` are demo values that are readable in the repository. Before a real deployment:

- Provide your own `ADMIN_PASSWORD` and `JWT_SECRET` as environment variables.
- Replace H2 with a production database, and manage schema changes.
- Disable the H2 console.
- Use a shared store such as Redis for the token blacklist.
- Add rate limiting on the login endpoints.
- Integrate a payment provider before handling real payments.
