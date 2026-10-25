# DifferentWorlds

A small bookstore REST API that I built to learn Spring Boot and Spring Security. Authors can submit books, customers put them in a cart and check out, and an admin looks after the platform.

I wasn't trying to build something huge. I wanted to understand how the pieces fit together: JWT login, roles, who is allowed to touch what, and how to return proper errors instead of a stack trace. This is my first project that goes all the way from signup to checkout.

Built with Java, Spring Boot, Spring Security (JWT), Spring Data JPA, Lombok and an in-memory H2 database.

## What's in it

- Signup and login for three kinds of users: customers, authors and admins. Logging out blacklists the token, so it stops working straight away.
- Each role can only reach its own endpoints, and users can only see their own data. Customer 2 can't read customer 1's cart, even with a valid token.
- Checkout happens on the server. The client only says who is paying and how. The server takes the price from the book, checks the stock, saves the purchase and the payment, and empties the cart. If one step fails, nothing is saved.
- Errors always come back in the same JSON shape with the right status code (400, 401, 403, 404, 409). For example, signing up with a username that exists gives a 409 with a clear message.
- Passwords are checked against a custom rule (8 to 64 characters, at least one letter and one digit) and stored with BCrypt.
- Nobody can sign up as an admin. The first admin is created when the app starts, and only an admin can create more.

## Running it

You need JDK 17 or newer and Maven.

```
mvn spring-boot:run
```

It starts on http://localhost:8080. The database lives in memory, so everything is wiped when you stop the app.

On the first start the app creates an admin from `application.properties`. The login is `admin` and the password is `ChangeMe123`. That's just for trying it out locally. To use another password, set an `ADMIN_PASSWORD` environment variable before you start the app.

## Trying it in Postman

The `postman` folder has a collection with about 75 requests. Import it, then open the Collection Runner and run the whole thing. Each request has a check, so you get green or red for every one.

It goes through setup (creating users and logging in), error messages, which role can do what, a full checkout, trying to read other people's data, changing a password, and logging out. You can run it again and again.

## Endpoints

Send the token in the header as `Authorization: Bearer <token>`.

**Auth**, `/api/auth`

| Method | Path | Who |
|---|---|---|
| POST | /signup/customer, /signup/author | anyone |
| POST | /signup/admin | admin |
| POST | /login/customer, /login/author, /login/admin | anyone |
| POST | /logout | anyone logged in |

**Store**, `/api/store`

| Method | Path | Who |
|---|---|---|
| GET | /, /{id} | anyone logged in |
| POST | / | admin |
| DELETE | /{id} | admin |
| PATCH | /{id}/stock/increase, /{id}/stock/decrease, /{id}/discount | admin |

**Authors**, `/api/authors` (the author themselves, or an admin)

| Method | Path |
|---|---|
| GET | /{id}, /by-username/{userName} |
| PATCH | /{id}/password |
| DELETE | /{id} |
| POST | /works |
| GET | /works/{workId} |

**Customers**, `/api/customers` (the customer themselves, or an admin; listing everyone is admin only)

| Method | Path |
|---|---|
| GET | / (admin only), /{id}, /by-username/{userName} |
| PATCH | /{id}/password, /{id}/email |
| DELETE | /{id} |

**Cart**, `/api/cart` (the customer only)

| Method | Path |
|---|---|
| GET | /{customerId} |
| POST | /{customerId}/items |
| PATCH | /{customerId}/items/{itemId} |
| DELETE | /{customerId}/items/{itemId}, /{customerId} |

**Purchases**, `/api/purchases`

| Method | Path | Who |
|---|---|---|
| POST | / (checkout) | the customer |
| GET | /{customerId} (history) | the customer, or admin |

**Payments** and **Admin** (admin only)

| Method | Path |
|---|---|
| GET | /api/payments, /api/payments/{id}, /api/payments/between |
| GET | /api/admin/{id}, /api/admin/by-username/{userName} |
| PATCH | /api/admin/{id}/password |
| PATCH | /api/admin/works/{workId}/approve, /reject |
| DELETE | /api/admin/{id} |

## What I'd improve next

- Approving and rejecting a work doesn't do anything yet. The endpoints exist, but there is no status behind them, so every work shows up in the store.
- The token blacklist and the database are both in memory, so a restart clears them. With more than one server, the blacklist would need something shared like Redis.
- If two people buy the last copy at exactly the same moment, it could be oversold. Fixing that needs locking in the database.
- The H2 console (a web page for looking inside the database) is open at `/h2-console` because it's handy while developing. Anyone who could reach it on a real server could read the data, so it has to be closed before deploying.

## Before using this for real

The passwords and keys in `application.properties` are demo values that anyone can read on GitHub. For a real deployment, set your own `ADMIN_PASSWORD` and `JWT_SECRET` as environment variables, switch from H2 to a real database such as PostgreSQL, and turn the H2 console off.