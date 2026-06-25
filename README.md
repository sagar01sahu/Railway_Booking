# Railway Ticket Booking API

A robust, stateless REST API built with Spring Boot for managing railway train schedules and ticket bookings. This enterprise-grade application features Role-Based Access Control (RBAC) via JSON Web Tokens (JWT), concurrency management to prevent double-booking, and Redis caching for high-performance data retrieval.

## 🚀 Tech Stack

* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** MySQL (Spring Data JPA / Hibernate)
* **Caching:** Redis Cloud
* **Security:** Spring Security & JJWT (JSON Web Tokens)
* **Tools:** Lombok, Postman (Testing)

---

## 🏗️ Architecture & Core Features

### 1. Stateless JWT Authentication
The API is completely stateless. Users authenticate once via the `/public/login` endpoint using their credentials to receive a JWT. This token must be passed in the `Authorization` header as a `Bearer` token for all subsequent secure requests.

* **ROLE_CLIENT:** Standard users who can search for trains and book tickets.
* **ROLE_ADMIN:** Administrators who can add new trains to the system and view global booking data.

### 2. High-Traffic Concurrency Control (Optimistic Locking)
To prevent race conditions where two users attempt to book the final available seat simultaneously, the `Train` entity implements **Optimistic Locking** using JPA's `@Version` annotation.
* If a simultaneous booking occurs, the system accepts the first transaction and automatically throws an `ObjectOptimisticLockingFailureException` for the second, safely cancelling it without corrupting the database.

### 3. Redis Caching for Performance
Train schedules are highly read-heavy but rarely modified. The application integrates with Redis to cache search queries.
* **`@Cacheable`:** Search results for specific routes (e.g., `New York-Boston`) are stored in Redis RAM. Subsequent queries bypass the SQL database, reducing response times from ~50ms to ~2ms.
* **`@CacheEvict`:** When an Admin adds a new train to a route, the cache for that specific route is automatically purged to ensure data consistency.

---

## 📡 API Endpoints

### 🔓 Public Endpoints (No Token Required)

#### 1. Register User
Creates a new user with `ROLE_CLIENT` privileges.
* **POST** `/public/register`
* **Body:**
    ```json
    {
      "username": "johndoe",
      "password": "securepassword",
      "email": "john@example.com",
      "phone": "1234567890"
    }
    ```

#### 2. Login
Authenticates credentials and returns a generated JWT (Valid for 10 Hours).
* **POST** `/public/login`
* **Body:**
    ```json
    {
      "username": "johndoe",
      "password": "securepassword"
    }
    ```

#### 3. Search Trains
Finds available trains for a specific route. (Cached via Redis).
* **POST** `/public/getTrain`
* **Body:**
    ```json
    {
      "source": "New York",
      "destination": "Boston"
    }
    ```

#### 4. Book a Ticket
Decrements available seats and generates a unique PNR.
* **POST** `/public/bookTicket`
* **Body:**
    ```json
    {
      "userId": 1,
      "trainId": 1,
      "passengerName": "John Doe",
      "age": 30,
      "startDate": "2026-06-25"
    }
    ```

### 🔒 Admin Endpoints (Requires JWT + ROLE_ADMIN)

*Note: All requests below must include the `Authorization: Bearer <token>` header.*

#### 1. Create a New Train
Adds a new train to the schedule. (Automatically evicts relevant Redis caches).
* **POST** `/admin/createTrain`
* **Body:**
    ```json
    {
      "trainNumber": "EXP123",
      "trainName": "Express 123",
      "source": "New York",
      "destination": "Boston",
      "departureTime": "08:00:00",
      "arrivalTime": "12:00:00",
      "totalSeats": 100
    }
    ```

#### 2. View All Trains
* **GET** `/admin/allTrains`

#### 3. View All Booked Tickets
Retrieves the global ledger of all tickets booked across the platform.
* **GET** `/admin/allTickets`

---

## ⚙️ Setup & Installation

### Prerequisites
* Java 17 or higher
* MySQL Server running on default port (3306)
* Redis instance (Local or Cloud)

### Environment Configuration
Update your `src/main/resources/application.properties` file with your specific database and Redis credentials:

```properties
# Server
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/railway_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Redis Configuration
spring.cache.type=redis
spring.data.redis.host=your-redis-host.db.redis.io
spring.data.redis.port=18825
spring.data.redis.username=default
spring.data.redis.password=your_redis_password