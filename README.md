# E-Commerce System Backend

A simple e-commerce marketplace backend built with **Spring Boot**, following a classic **Controller → Service → Model** layered architecture. Multiple merchants can sell products across categories, and customers can browse, buy, and top up their wallet balance.

> ⚠️ **In-memory storage**: this project currently stores all data in `ArrayList`s inside each service (no database). Data resets every time the application restarts.

---

## Tech Stack

- **Java** + **Spring Boot** (`spring-boot-starter-web`)
- **Lombok** — `@Data`, `@AllArgsConstructor`, `@RequiredArgsConstructor`
- **Jakarta Bean Validation** (`@NotEmpty`, `@Pattern`, `@Size`, `@Positive`, `@Min`, etc.)
- **Maven**

---

## Architecture

```
Controller  → receives HTTP requests, validates input, returns ResponseEntity + status code
    ↓
Service     → business logic, in-memory ArrayList storage, lookups, int result codes
    ↓
Model       → data classes (Lombok + validation annotations)
```

Each domain follows this same three-layer pattern, wired together with `@RequiredArgsConstructor` constructor injection.

---

## Domains

| Model | Fields | Id Prefix |
|---|---|---|
| **User** | id, username, password, email, role (`Admin`/`Customer`), balance | `U-` |
| **Product** | id, name, price, categoryId | `P-` |
| **Category** | id, name | `C-` |
| **Merchant** | id, name | `M-` |
| **MerchantStock** | id, productId, merchantId, stock | `MS-` |

`MerchantStock` is the bridge entity linking `Product` ↔ `Merchant` — it tracks which merchant sells which product, and how much stock they have.

---

## Design Conventions

- **Int result codes** — most service methods return an `int` instead of a plain `boolean`, so the controller can report exactly what went wrong:
  - `0` = success
  - `1`, `2`, `3`, … = a distinct failure reason (checked in the order the code performs its lookups/validation)
- **Prefixed identifiers** — every `id` field is validated with a class-specific prefix (see table above), catching a mismatched id before it reaches a service lookup.
- **Validation messages** — every Bean Validation constraint carries a `message`, surfaced to the client via Spring's `Errors` object on `@Valid` failures.
- **Consistent REST shape** — every controller exposes `GET /get`, `POST /add`, `PUT /update/{id}`, `DELETE /delete/{id}`, plus any domain-specific action endpoints.
- Responses are wrapped in `ApiResponse` (for action/status messages) or return the raw list/object directly (for `GET` endpoints).

---

## API Endpoints

Base path: `/api/v1`

### `/user`
| Method | Path | Description |
|---|---|---|
| GET | `/get` | List all users |
| POST | `/add` | Create a user |
| PUT | `/update/{id}` | Update a user |
| DELETE | `/delete/{id}` | Delete a user |
| PUT | `/buy` | Buy a product from a merchant (`userId`, `productId`, `merchantId`) |
| PUT | `/addBalance` | Top up a user's wallet balance (`userId`, `amount`) |
| POST | `/addMerchant` | Register a new merchant — **Admin only** (`userId`, `merchantId`, `merchantName`) |

### `/product`
| Method | Path | Description |
|---|---|---|
| GET | `/get` | List all products |
| POST | `/add` | Create a product |
| PUT | `/update/{id}` | Update a product |
| DELETE | `/delete/{id}` | Delete a product |
| PUT | `/discount` | Apply a percentage discount to a product's price (`productId`, `percentage`) |

### `/category`
| Method | Path | Description |
|---|---|---|
| GET | `/get` | List all categories |
| POST | `/add` | Create a category |
| PUT | `/update/{id}` | Update a category |
| DELETE | `/delete/{id}` | Delete a category |
| GET | `/getCategoryProducts/{id}` | Get all products belonging to a category |

### `/merchant`
| Method | Path | Description |
|---|---|---|
| GET | `/get` | List all merchants |
| POST | `/add` | Create a merchant |
| PUT | `/update/{id}` | Update a merchant |
| DELETE | `/delete/{id}` | Delete a merchant |
| GET | `/getMerchantStocks/{merchantId}` | Get all stock entries for a merchant |

### `/merchantStock`
| Method | Path | Description |
|---|---|---|
| GET | `/get` | List all merchant stock entries |
| POST | `/add` | Create a merchant stock entry |
| PUT | `/update/{id}` | Update a merchant stock entry |
| DELETE | `/delete/{id}` | Delete a merchant stock entry |
| PUT | `/addStock` | Increase stock for a product at a merchant (`productId`, `merchantId`, `stock`) |

---

## New Endpoints (this iteration)

Added without introducing any new attributes or classes — all derived from existing data:

| Endpoint | Purpose |
|---|---|
| `discount(productId, percentage)` | Apply a percentage discount to a product's price. Enables flash sales / promotions. |
| `addBalance(userId, amount)` | Top up a customer's wallet balance. |
| `addMerchant(userId, merchantId, merchantName)` | Admin-gated merchant onboarding — checks `role == "Admin"` before creating the merchant. |
| `GetCategoryProducts(categoryId)` | Filters the product list by `categoryId` — powers a storefront category page. |
| `getMerchantStocks(merchantId)` | Filters the `MerchantStock` list by `merchantId` — gives a merchant a full inventory view. |

---

## Result Codes Reference

| Method | 0 | 1 | 2 | 3 | 4 | 5 |
|---|---|---|---|---|---|---|
| `buyProduct` | Success | User not found | Product not found | Merchant not found | Out of stock | Insufficient balance |
| `discount` | Success | Product not found | Invalid percentage | — | — | — |
| `addBalance` | Success | User not found | Invalid amount | — | — | — |
| `addMerchant` | Success | User not found | Not an Admin | — | — | — |

---

## Project Structure

```
src/main/java/com/example/tuwaiqcapstone1/
├── Controller/
│   ├── UserController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── MerchantController.java
│   └── MerchantStockController.java
├── Service/
│   ├── UserService.java
│   ├── ProductService.java
│   ├── CategoryService.java
│   ├── MerchantService.java
│   └── MerchantStockService.java
├── Model/
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Merchant.java
│   └── MerchantStock.java
└── Api/
    └── ApiResponse.java
```
